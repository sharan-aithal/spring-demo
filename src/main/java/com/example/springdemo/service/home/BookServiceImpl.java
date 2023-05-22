package com.example.springdemo.service.home;

import com.example.springdemo.entity.home.Book;
import com.example.springdemo.repository.home.BookCrudRepository;
import com.example.springdemo.repository.home.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    // @Qualifier("mySqlBookRepository")
    private BookRepository repository;

    @Autowired
    private BookCrudRepository bookCrudRepository;

    @Override
    public Book getBook(int bookId) {
        System.out.println("getting book " + bookId);
        return repository.getBookById(bookId);
    }

    @Override
    public long bookCount() {
        return repository.getBookCount();
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.getBooks();
    }

    @Override
    public void addBook(Book book) {
        repository.insertBook(book);
    }

    @Override
    public void updateBook(int id, String title) {
        repository.updateBook(id, title);
    }

    @Override
    public void deleteBook(int id) {
        repository.deleteBook(id);
    }

    @Override
    public List<Book> getBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "id");
        Page<Book> allBooksByPaging = bookCrudRepository.findAll(pageable);
        return allBooksByPaging.getContent();
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookCrudRepository.queryBookByTitle(title);
    }

    @Override
    public Book updateBookAuthor(String author, int id) {
        bookCrudRepository.updateBookByAuthor(author, id);
        return repository.getBookById(id);
    }

    @Override
    public boolean deleteBookBoolean(int id) {
        int result = bookCrudRepository.deleteBookById(id);
        System.out.println(result);
        return result == 1;
    }

    @Override
    public Book addBookAndReturn(Book book) {
        return null;
    }
}
