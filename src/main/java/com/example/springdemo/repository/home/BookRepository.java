package com.example.springdemo.repository.home;

import com.example.springdemo.entity.home.Book;

import java.util.List;


public interface BookRepository {
    String getBookNameById(int bookId);

    Long getBookCount();

    Book getBookById(int id);

    List<Book> getBooks();

    void insertBook(Book book);

    void updateBook(int id, String title);

    void deleteBook(int id);
}
