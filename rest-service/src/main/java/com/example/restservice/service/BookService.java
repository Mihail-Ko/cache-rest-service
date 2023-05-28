package com.example.restservice.service;

import com.example.restservice.mapper.BookMapper;
import com.example.restservice.model.BookModel;
import com.example.restservice.repository.CustomBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final CustomBookRepository bookRepository;
    private final BookMapper mapper;

    public BookModel addBook(BookModel book) {
        return mapper.toModel(
            bookRepository.save(
                mapper.toEntity(book)
            )
        );
    }

    public List<BookModel> getAll(int pageN) {
        return mapper.toModelList(
            bookRepository.findAll(
                    PageRequest.of(pageN - 1, 10)
                )
                .getContent()
        );
    }

    public BookModel getOne(long id) {
        return mapper.toModel(
            bookRepository.findById(id)
                .orElseThrow(NoSuchElementException::new)
        );
    }

    public long delete(long id) {
        checkElementExist(id);
        bookRepository.deleteById(id);
        return id;
    }

    public BookModel update(BookModel updatedBook) {
        checkElementExist(
            updatedBook.getId() // NoSuchElementException
        );
        return mapper.toModel(
            bookRepository.save(
                mapper.toEntity(updatedBook)
            )
        );
    }

    private void checkElementExist(long id) {
        if (bookRepository.findById(id).isEmpty())
            throw new NoSuchElementException();
    }
}