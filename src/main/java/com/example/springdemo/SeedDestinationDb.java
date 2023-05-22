package com.example.springdemo;

import com.example.springdemo.entity.Destination;
import com.example.springdemo.entity.Location;
import com.example.springdemo.entity.Review;
import com.example.springdemo.entity.User;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class SeedDestinationDb {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<User> users;
    private List<Location> locations;
    private List<Destination> destinations;
    private ArrayList<Review> reviews;
    private Faker fake;

    private static final Log log = LogFactory.getLog(SeedDestinationDb.class);


    @PostConstruct
    private void init() {
        createFake();

        createUsers();
        List<Object[]> userListObj = new ArrayList<>();
        for (User user : users) {
            userListObj.add(new Object[]{user.getName(), user.getEmail()});
        }
        jdbcTemplate.batchUpdate("insert into users (name, email) values (?, ?)", userListObj);
        log.debug("creating users");

        createLocations();
        List<Object[]> locationListObj = new ArrayList<>();
        for (Location location : locations) {
            locationListObj.add(new Object[]{location.getLocation(), location.getCity(), location.getState(), location.getCountry(), location.getZipCode()});
        }
        jdbcTemplate.batchUpdate("insert into locations (location, city, state, country, zip_code) values (?, ?, ?, ?, ?)", locationListObj);
        log.debug("creating locations");

        createDestinations();
        List<Object[]> destListObj = new ArrayList<>();
        for (Destination destination : destinations) {
            Integer locationId = jdbcTemplate.queryForObject("select id from locations where location = ?1 and city = ?2 and state = ?3 and country = ?4 and zip_code = ?5", Integer.class,
                    destination.getLocation().getLocation(), destination.getLocation().getCity(), destination.getLocation().getState(), destination.getLocation().getCountry(), destination.getLocation().getZipCode());
            destListObj.add(new Object[]{destination.getName(), destination.getDescription(), locationId});
        }
        jdbcTemplate.batchUpdate("insert into destinations (name, description, location_id) values (?, ?, ?)", destListObj);
        log.debug("creating destinations");

        createReviews();
        List<Object[]> reviewListObj = new ArrayList<>();
        for (Review review : reviews) {
            Integer userId = jdbcTemplate.queryForObject(
                    "select id from users where name = ?1 and email = ?2",
                    Integer.class,
                    review.getUser().getName(), review.getUser().getEmail());
            reviewListObj.add(new Object[]{review.getComment(), review.getRating(), userId, fake.random().nextInt(1, destinations.size())});
        }
        jdbcTemplate.batchUpdate("insert into reviews (comment, rating, user_id, destination_id) values (?, ?, ?, ?)", reviewListObj);
        log.debug("creating reviews");
    }


    private void createFake() {
        fake = new Faker(new Locale("en-IND"));
    }

    public void createUsers() {
        int companyCount = 5;
        int userCount = 50;
        users = new ArrayList<>();
        List<String> companyList = new ArrayList<>();
        for (int i = 0; i < companyCount; i++) {
            companyList.add(fake.company().url());
        }
        for (int i = 0; i < userCount; i++) {
            User user = new User();
            Name name = fake.name();
            String uname = name.username();

            user.setName(uname + " (" + name.fullName() + ")");
            user.setEmail(uname + "@" + companyList.get(fake.random().nextInt(companyCount)));
            users.add(user);
        }
    }

    private void createLocations() {
        int locationCount = 10;
        locations = new ArrayList<>();
        for (int i = 0; i < locationCount; i++) {
            Location location = new Location();
            location.setLocation(fake.address().cityName());
            location.setCity(fake.address().city());
            location.setState(fake.address().state());
            location.setCountry(fake.address().country());
            location.setZipCode(fake.address().zipCode());
            locations.add(location);
        }
    }

    public void createDestinations() {

        destinations = new ArrayList<>();
        int destinationCount = 25;
        for (int i = 0; i < destinationCount; i++) {
            Destination destination = new Destination();
            Location location = locations.get(fake.random().nextInt(locations.size()));
            destination.setName(location.getLocation());
            destination.setDescription(location.getLocation() + " - " + location.getState() + " - " + location.getCountry());
            destination.setLocation(location);
            destinations.add(destination);
        }
    }

    public void createReviews() {
        int reviewCountPerDest = 10;
        reviews = new ArrayList<>();
        for (int j = 0; j < destinations.size(); j++) {
            for (int i = 0; i < reviewCountPerDest; i++) {
                Review review = new Review();
                review.setComment(fake.lorem().characters(100, 240));
                review.setRating(fake.random().nextInt(1, 5));
                review.setUser(users.get(fake.random().nextInt(users.size())));
                reviews.add(review);
            }
        }
    }
}
