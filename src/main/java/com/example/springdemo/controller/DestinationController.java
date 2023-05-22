package com.example.springdemo.controller;

import com.example.springdemo.entity.Destination;
import com.example.springdemo.entity.Review;
import com.example.springdemo.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class DestinationController {

    @Autowired
    private DestinationService service;

    @GetMapping("")
    public ResponseEntity<List<Destination>> listDestinations() {
        List<Destination> destinations = service.getAllDestinations();
        return ResponseEntity.ok().body(destinations);
    }

    @PostMapping("")
    public ResponseEntity<Object> addDocument(@RequestBody Destination destination) {
        if (destination.getId() == 0) {
            service.addDestination(destination);
            return ResponseEntity.created(URI.create("/destinations/" + destination.getId())).body(destination);
        } else
            return ResponseEntity.badRequest().body("invalid body");
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getDestination(@PathVariable("id") String ids) {
        try {
            int id = parseId(ids);

            Destination destination = service.getDestination(id);
            if (destination.getId() == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("destination id not found");
            return ResponseEntity.ok().body(destination);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("invalid id");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateDestination(@PathVariable("id") String ids, @RequestBody Destination destination) {
        try {
            int id = parseId(ids);
            service.updateDestination(id, destination);
            if (destination.getId() == 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("destination id not found");
            return ResponseEntity.ok().body(destination);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("invalid id");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteDestination(@PathVariable("id") String ids) {
        int id = 0;
        try {
            id = parseId(ids);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("invalid id");
        }
        if (service.isDestinationExists(id)) {
            service.deleteDestination(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("destination id not found");
    }

    @GetMapping("{id}/reviews")
    public ResponseEntity<Object> getDestinationReviews(@PathVariable("id") String ids) {
        try {
            int id = parseId(ids);
            List<Review> reviews = service.getDestinationReviews(id);
            return ResponseEntity.ok().body(reviews);
        } catch (NumberFormatException e) {
            return ResponseEntity.ok().body("invalid id");
        }
    }

    private int parseId(String id) throws NumberFormatException {
        return Integer.parseInt(id);
    }
}
