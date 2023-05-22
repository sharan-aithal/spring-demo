package com.example.springdemo.configuration;

import com.example.springdemo.entity.home.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


@Configuration
@Lazy
public class BookProviderConfig {

    public BookProviderConfig() {
        System.out.println("Book Provider constructor called");
    }

    @Bean
    @Scope("singleton")
    @Lazy
    public Book bookBean() {
        System.out.println("getting bean for bookBean");
        return new Book(1, "A new Generation Workflow", "William Backer");
    }

    @Bean
    @Lazy
    public Book loadBookBean(int bookId, String title, String author) {
        return new Book(bookId, title, author);
    }

    @Bean
    @Lazy
    public Book loadAnotherBookBean(@Qualifier("loadBookBean") Book book) {
        return new Book(book.getId() + 1, book.getTitle(), book.getAuthor());
    }
}
