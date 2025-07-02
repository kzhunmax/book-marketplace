package com.example.bookmanager.controller;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "{id}")
    Book getBookById(@PathVariable(name = "id") Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping(path = "{id}")
    Book updateBook(@PathVariable(name = "id") Long id, Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping(path = "{id}")
    void deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);
    }
}
