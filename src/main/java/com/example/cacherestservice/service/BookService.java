package com.example.cacherestservice.service;

import com.example.cacherestservice.mapper.BookMapper;
import com.example.cacherestservice.model.BookModel;
import com.example.cacherestservice.entity.BookEntity;
import com.example.cacherestservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public List<BookModel> getAll(int pageN) {
        Pageable page = PageRequest.of(pageN - 1,10);
        return mapper.toModelList(
                bookRepository.findAll(page)
                        .getContent()
        );
    }

    public BookModel getOne(Long id) {
        BookEntity book = bookRepository.findById(id).get();
        return mapper.toModel(book);
    }

    public Long delete(Long id) {
        bookRepository.findById(id).get();
        bookRepository.deleteById(id);
        return id;
    }

    public BookModel update(BookEntity updatedBook) {
        BookEntity book = bookRepository.findById(updatedBook.getId()).get();
        book.setName(updatedBook.getName());
        book.setAuthor(updatedBook.getAuthor());
        book.setYear(updatedBook.getYear());
        return mapper.toModel(bookRepository.save(book));
    }
}
