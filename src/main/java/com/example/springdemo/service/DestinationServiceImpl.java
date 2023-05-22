package com.example.springdemo.service;

import com.example.springdemo.entity.Destination;
import com.example.springdemo.entity.Location;
import com.example.springdemo.entity.Review;
import com.example.springdemo.repository.DestinationRepository;
import com.example.springdemo.repository.LocationRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService {

    private static final Log log = LogFactory.getLog(DestinationServiceImpl.class);

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private LocationRepository locationRepository;


    @Override
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public void addDestination(Destination destination) {
        Location saveLoc = destination.getLocation();
        if (saveLoc.getId() != 0) {
            log.info("saving existing location");
            this.addDestinationByLocationId(destination, saveLoc.getId());
        } else {
            Location loc = locationRepository.findByLocation(saveLoc.getLocation());
            if (loc != null) {
                saveLoc.setId(loc.getId());
            } else {
                locationRepository.save(saveLoc);
                log.info("saving new location");
            }
            destination.setLocation(saveLoc);
            destinationRepository.save(destination);
        }
    }

    @Override
    public void addDestinationByLocationId(Destination destination, int locationId) {
        Optional<Location> loc = locationRepository.findById(locationId);
        if (loc.orElse(null) != null) {
            destination.setLocation(loc.get());
            destinationRepository.save(destination);
        }
    }

    @Override
    public Destination getDestination(int id) {
        return destinationRepository.findById(id).orElse(new Destination());
    }

    @Override
    public void updateDestination(int id, Destination destination) {
        Optional<Destination> dest = destinationRepository.findById(id);
        if (dest.isPresent()) {
            destination.setId(dest.get().getId());
            if (destination.getLocation().getId() != 0) {
                Optional<Location> location = locationRepository.findById(destination.getLocation().getId());
                if (location.isPresent())
                    destination.setLocation(location.get());
            }
            destinationRepository.save(destination);
        }
    }

    @Override
    public void deleteDestination(int id) {
        destinationRepository.deleteById(id);
    }

    @Override
    public List<Review> getDestinationReviews(int id) {
        return destinationRepository.findReviewsById(id);
    }

    @Override
    public boolean isDestinationExists(int id) {
        return destinationRepository.findById(id).isPresent();
    }
}
