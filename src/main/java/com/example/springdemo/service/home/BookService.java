package com.example.springdemo.service.home;


import com.example.springdemo.entity.home.Book;

import java.util.List;

public interface BookService {
    public Book getBook(int bookId);

    public long bookCount();

    List<Book> getAllBooks();

    void addBook(Book book);

    void updateBook(int id, String title);

    void deleteBook(int id);


    List<Book> getBooks(int pageNo, int pageSize);

    List<Book> getBooksByTitle(String title);

    Book updateBookAuthor(String author, int id);

    boolean deleteBookBoolean(int id);

    Book addBookAndReturn(Book book);
}
