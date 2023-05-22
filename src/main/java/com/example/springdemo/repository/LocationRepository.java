package com.example.springdemo.repository;

import com.example.springdemo.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Location findByLocation(String location);
}
