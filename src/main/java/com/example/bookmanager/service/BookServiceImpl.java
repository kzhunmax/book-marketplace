package com.example.bookmanager.service;

import com.example.bookmanager.exception.BookAlreadyExistsException;
import com.example.bookmanager.exception.BookNotFoundException;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Book createBook(Book book) {
        boolean exists = bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (exists) {
            throw new BookAlreadyExistsException("Book already exists with same title and author");
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        if (isBookSame(existingBook, book)) {
            System.out.println("Book update skipped — no changes detected");
            return existingBook;
        }
        updateBookFields(existingBook, book);
        return bookRepository.save(existingBook);
    }

    private void updateBookFields(Book existingBook, Book updatedBook) {
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setDescription(updatedBook.getDescription());
    }

    private boolean isBookSame(Book a, Book b) {
        return Objects.equals(a.getTitle(), b.getTitle()) &&
                Objects.equals(a.getAuthor(), b.getAuthor()) &&
                Objects.equals(a.getPrice(), b.getPrice()) &&
                Objects.equals(a.getDescription(), b.getDescription());
    }

    @Override
    public void deleteBook(Long id) {
        getBookById(id);
        bookRepository.deleteById(id);
    }
}
