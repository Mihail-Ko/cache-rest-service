package com.example.restservice.service.wrapper;

import com.example.restservice.model.BookModel;
import com.example.restservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceWrapper {

    private final BookService bookService;

    public BookModel addBook(BookModel book) {
        log.info("Запрос на добавление книги");
        return bookService.addBook(book);
    }

    public List<BookModel> getAll(int pageN) {
        log.info("Запрос на получение списка книг");
        return bookService.getAll(pageN);
    }

    public BookModel getOne(long id) {
        log.info("Запрос на получение книги");
        return bookService.getOne(id);
    }

    public long delete(long id) {
        log.info("Запрос на удаление книги");
        return bookService.delete(id);
    }

    public BookModel update(BookModel book) {
        log.info("Запрос на обновление книги");
        return bookService.update(book);
    }
}