package com.example.restservice.controller;

import com.example.restservice.model.BookModel;
import com.example.restservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Invalidate cache")
    protected void resetCache() {
        bookService.clearCache();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить книгу")
    protected BookModel addBook(@RequestBody BookModel book) {
        return bookService.addBook(book);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить страницу списка книг")
    protected List<BookModel> getBooks(@RequestParam int page) {
        return bookService.getAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить книгу по id")
    protected BookModel getOneBook(@PathVariable long id) {
        return bookService.getOne(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Удалить книгу по id")
    protected String deleteBook(@PathVariable long id) {
        return "Удалена книга с id " + bookService.delete(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить книгу")
    protected BookModel updateBook(@RequestBody BookModel book) {
        return bookService.update(book);
    }
}
