package com.example.springdemo.service;

import com.example.springdemo.entity.Destination;
import com.example.springdemo.entity.Review;

import java.util.List;

public interface DestinationService {

    List<Destination> getAllDestinations();

    void addDestination(Destination destination);

    void addDestinationByLocationId(Destination destination, int locationId);

    Destination getDestination(int id);

    void updateDestination(int id, Destination destination);

    void deleteDestination(int id);

    List<Review> getDestinationReviews(int id);

    boolean isDestinationExists(int id);
}
