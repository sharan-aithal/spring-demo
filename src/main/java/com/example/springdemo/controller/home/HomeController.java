package com.example.springdemo.controller.home;

import com.example.springdemo.entity.home.Book;
import com.example.springdemo.service.home.BookService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class HomeController {

    @Value("${server.port}")
    private int port;
    @Autowired
    private BookService service;

    @GetMapping("")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().body("hello running on port " + port);
    }

    @GetMapping("1")
    public String homei() {
        return "home " + service.bookCount();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookFromId(@PathVariable("id") int id) {
        Book book = service.getBook(id);
        if (book != null)
            return new ResponseEntity<>(book, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Book());
    }

    @GetMapping("all")
    public ResponseEntity<List<Book>> all() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("new")
    public ResponseEntity<Book> insert() {
        Book book = new Book();
        Faker fake = new Faker(new Locale("en-IND"));

        com.github.javafaker.Book fakeBook = fake.book();
        book.setTitle(fakeBook.title());
        book.setAuthor(fakeBook.author());

        service.addBook(book);
        // return ResponseEntity.status(HttpStatus.CREATED).header("location", "/" + book.getId()).body(book);
        return ResponseEntity.created(URI.create("/home/"+book.getId())).body(book);
    }

    @GetMapping("{id}/update")
    public ResponseEntity<String> update(@PathVariable("id") int id) {
        Faker fake = new Faker(new Locale("en-IND"));

        service.updateBook(id, fake.book().title());
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }

    @GetMapping("{id}/update1")
    public ResponseEntity<Book> update1(@PathVariable("id") int id) {
        Faker fake = new Faker(new Locale("en-IND"));

        Book book = service.updateBookAuthor(fake.book().author(), id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("{id}/delete")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        service.deleteBook(id);
        return new ResponseEntity<>("DONE", HttpStatus.OK);
    }

    @GetMapping("{id}/delete1")
    public ResponseEntity<String> delete1(@PathVariable("id") int id) {
        if (service.deleteBookBoolean(id))
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed");
    }

    @GetMapping("pages")
    public ResponseEntity<List<Book>> getBooksPagination(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "limit", defaultValue = "10") int limit) {
        List<Book> books = service.getBooks(page, limit);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<Book>> searchBookTitle(@RequestParam(name = "q", defaultValue = "") String query) {
        List<Book> results = service.getBooksByTitle(query);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // @ExceptionHandler({Exception.class, RuntimeException.class})
    // public ResponseEntity<String> handleException(HttpServletRequest request, RuntimeException e) {
    //     System.out.println(e);
    //     return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    // }
}
