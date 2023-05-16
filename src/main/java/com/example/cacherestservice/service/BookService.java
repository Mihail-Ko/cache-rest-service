package com.example.cacherestservice.service;

import com.example.cacherestservice.entity.BookEntity;
import com.example.cacherestservice.model.BookModel;
import com.example.cacherestservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public List<BookModel> getAll() {
        List<BookEntity> entitiesList = (List<BookEntity>) bookRepository.findAll();
        return entitiesList.stream().map(BookModel::toModel).collect(Collectors.toList());
    }

    public BookModel getOne(Long id) {
        BookEntity book = bookRepository.findById(id).get();
        return BookModel.toModel(book);
    }

    public Long delete(Long id) {
        bookRepository.findById(id).get(); // в случаи отсутствия такой записи вызовется NoSuchElementException
        bookRepository.deleteById(id);
        return id;
    }

    public BookModel update(BookEntity updatedBook) {
        BookEntity book = bookRepository.findById(updatedBook.getId()).get();
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setYear(updatedBook.getYear());
        return BookModel.toModel(bookRepository.save(book));
    }
}
