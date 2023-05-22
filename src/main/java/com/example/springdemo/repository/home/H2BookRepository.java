package com.example.springdemo.repository.home;

import com.example.springdemo.entity.home.Book;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Primary
public class H2BookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getBookNameById(int bookId) {
        System.out.println("H2 book repository");
        return "H2 book repository: " + bookId;
    }

    @Override
    public Long getBookCount() {
        String jpql = "select count(b) from Book b";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        return query.getSingleResult();
    }

    @Override
    public Book getBookById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> getBooks() {
        String jpql = "select b from Book b";
        TypedQuery<Book> query = entityManager.createQuery(jpql, Book.class);
        return query.getResultList();
    }


    @Override
    @Transactional
    public void insertBook(Book book) {
        entityManager.persist(book);
    }

    @Override
    @Transactional
    public void updateBook(int id, String title) {
        Book book = entityManager.find(Book.class, id);
        book.setTitle(title);
        // entityManager.flush();
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null)
            entityManager.remove(book);
    }
}
