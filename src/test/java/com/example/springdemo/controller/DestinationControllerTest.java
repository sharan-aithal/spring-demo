package com.example.springdemo.controller;

import com.example.springdemo.entity.Destination;
import com.example.springdemo.entity.Location;
import com.example.springdemo.service.DestinationService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = {DestinationController.class})
class DestinationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DestinationService service;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listDestinations() throws Exception {
        Mockito.when(this.service.getAllDestinations()).thenReturn(getAll());
        mockMvc.perform(MockMvcRequestBuilders.get("/destinations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    private List<Destination> getAll() {
        List<Destination> dest = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Destination destination = new Destination();
            destination.setName("Palace Ground");
            destination.setDescription("A ground containing a palace");
            Location location = new Location();
            location.setLocation("Palace Ground - Grand Master");
            location.setCity("Grand Master");
            location.setState("Dark");
            location.setCountry("Lone");
            location.setZipCode("12345");
            destination.setLocation(location);
            dest.add(destination);
        }
        return dest;
    }

    @Test
    void addDocument() throws Exception {
        // Mockito.doAnswer(Answers.RETURNS_SELF).when(this.service).addDestination(new Destination());

        String body = "{\n" +
                "    \"name\": \"Kolkata22\",\n" +
                "    \"description\": \"Kolkata22 - Rajasthan - Saint Vincent and the Grenadines\",\n" +
                "    \"reviews\": [],\n" +
                "    \"location\": {\n" +
                "        \"id\": 4\n" +
                "    }\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/destinations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // .andExpect(MockMvcResultMatchers.header().string("location", "/destinations/26"));
    }

    @Test
    void getDestination() throws Exception {
        int id = 12;
        Mockito.when(this.service.getDestination(id)).thenReturn(getOneDestination());

        mockMvc.perform(MockMvcRequestBuilders.get("/destinations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(id)));
    }

    private Destination getOneDestination() {
        Destination d = new Destination();
        d.setId(12);
        d.setName("Palace Ground");
        d.setDescription("desc");
        d.setReviews(new ArrayList<>());
        Location loc = new Location();
        loc.setId(1);
        loc.setLocation("Location 1");
        loc.setCity("city 1");
        loc.setState("state 1");
        loc.setCountry("country 1");
        loc.setZipCode("123456");
        d.setLocation(loc);

        return d;
    }

    @Test
    void updateDestination() throws Exception {
        int id = 1;
        Mockito.doNothing().when(this.service).updateDestination(id, new Destination());
        String body = "{\n" +
                "    \"name\": \"Kolkata22\",\n" +
                "    \"description\": \"Kolkata22 - Rajasthan - Saint Vincent and the Grenadines\",\n" +
                "    \"reviews\": [],\n" +
                "    \"location\": {\n" +
                "        \"id\": 4\n" +
                "    }\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/destinations/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Kolkata22")));
    }

    @Test
    void deleteDestination() {
    }

    @Test
    void getDestinationReviews() {
    }
}