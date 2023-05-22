package com.example.springdemo.repository.home;

import com.example.springdemo.entity.home.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OracleBookRepository implements BookRepository {
    @Override
    public String getBookNameById(int bookId) {
        System.out.println("Oracle book");
        return "Oracle book";
    }

    @Override
    public Long getBookCount() {
        return null;
    }

    @Override
    public Book getBookById(int id) {
        return null;
    }

    @Override
    public List<Book> getBooks() {
        return null;
    }

    @Override
    public void insertBook(Book book) {

    }

    @Override
    public void updateBook(int id, String title) {

    }

    @Override
    public void deleteBook(int id) {

    }
}
