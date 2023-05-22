package com.example.springdemo.repository;

import com.example.springdemo.entity.Destination;
import com.example.springdemo.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DestinationRepository extends CrudRepository<Destination, Integer> {

    @Override
    List<Destination> findAll();

    List<Review> findReviewsById(int id);
}
