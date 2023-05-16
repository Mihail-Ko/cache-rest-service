package com.example.cacherestservice.controller;

import com.example.cacherestservice.entity.BookEntity;
import com.example.cacherestservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity addBook(@RequestBody BookEntity book) {
        try {
            bookService.addBook(book);
            return ResponseEntity.ok("Книга сохранена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getBooks() {
        try {
            return ResponseEntity.ok(bookService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getOne(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Книга не найдена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok("Удалена книга с id "+bookService.delete(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Книга не найдена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity updateBook(@RequestBody BookEntity book) {
        try {
            return ResponseEntity.ok(bookService.update(book));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Книга не найдена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
