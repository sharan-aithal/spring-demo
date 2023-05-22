package com.example.springdemo;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class SeedDb {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        Faker fake = new Faker(new Locale("en-IN"));
        Book book = fake.book();

        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            jdbcTemplate.update("insert into books(title, author) values (?,?)", book.title(), book.author());
        }
        // jdbcTemplate.update("insert into books(title, author) values (?,?)", "Sometime you like it","Michel Perk");
    }
}
