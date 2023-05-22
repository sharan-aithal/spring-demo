package com.example.springdemo.repository.home;

import com.example.springdemo.entity.home.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BookCrudRepository extends CrudRepository<Book, Integer> {

    Page<Book> findAll(Pageable pageable);

    @Query("select b from Book b where b.title like %:title%")
    List<Book> queryBookByTitle(@Param("title") String title);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update Book b set b.author = ?1 where b.id = ?2")
    int updateBookByAuthor(String author, int id);

    @Transactional
    @Modifying
    @Query("delete Book b where b.id = :id")
    int deleteBookById(@Param("id") int id);
}
