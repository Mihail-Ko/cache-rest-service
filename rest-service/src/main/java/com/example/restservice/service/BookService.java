package com.example.restservice.service;

import com.example.restservice.mapper.BookMapper;
import com.example.restservice.model.BookModel;
import com.example.restservice.repository.CustomBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final CustomBookRepository bookRepository;
    private final BookMapper mapper;
    private final String cacheNameBook = "books";
    private final String cacheNamePage = "pages";

    @CacheEvict(cacheNames = cacheNamePage, allEntries = true)
    public BookModel addBook(BookModel book) {
        return mapper.toModel(
            bookRepository.insert(
                mapper.toEntity(book)));
    }

    @Cacheable(value = cacheNamePage, key = "#pageN", sync=true)
    public List<BookModel> getAll(int pageN) {
        return mapper.toModelList(
            bookRepository.findAll(
                    PageRequest.of(pageN - 1, 10))
                .getContent());
    }

    @Cacheable(value = cacheNameBook, key = "#id", sync=true)
    public BookModel getOne(long id) {
        return mapper.toModel(
            bookRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Caching(evict = {
        @CacheEvict(value = cacheNameBook, key = "#id"),
        @CacheEvict(cacheNames = cacheNamePage, allEntries = true)})
    public long delete(long id) {
        checkElementExist(id);
        bookRepository.deleteById(id);
        return id;
    }

    @Caching(
        put = @CachePut(value = cacheNameBook, key = "#book.id"),
        evict = @CacheEvict(cacheNames = cacheNamePage, allEntries = true))
    public BookModel update(BookModel book) {
        checkElementExist(
            book.getId());
        return mapper.toModel(
            bookRepository.update(
                mapper.toEntity(book)));
    }

    private void checkElementExist(long id) {
        if (bookRepository.findById(id).isEmpty())
            throw new NoSuchElementException();
    }
}