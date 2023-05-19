package com.example.cacherestservice.controller;

import com.example.cacherestservice.model.BookModel;
import com.example.cacherestservice.entity.BookEntity;
import com.example.cacherestservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private String addBook(@RequestBody BookEntity book) {
        bookService.addBook(book);
        return "Книга сохранена";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<BookModel> getBooks(@RequestParam int page) {
        return bookService.getAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private BookModel getOneBook(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private String deleteBook(@PathVariable Long id) {
        return "Удалена книга с id " + bookService.delete(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    private BookModel updateBook(@RequestBody BookEntity book) {
        return bookService.update(book);
    }
}
