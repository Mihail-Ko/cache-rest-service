package com.example.restservice.service;

import com.example.restservice.cache.BooksCache;
import com.example.restservice.entity.BookEntity;
import com.example.restservice.mapper.BookMapper;
import com.example.restservice.model.BookModel;
import com.example.restservice.repository.CustomBookRepository;
import com.hazelcast.cp.lock.FencedLock;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final CustomBookRepository bookRepository;
    private final BookMapper mapper;
    private final HazelcastCacheManager cacheManager;
    private final BooksCache booksCache;
    private final int pageLen = 5;
    @Value("${delay.getOne}")
    private int delayGetOne;
    @Value("${delay.getAll}")
    private int delayGetAll;

    public BookModel addBook(BookModel book) {
        BookEntity savedBook = bookRepository.insert(
            mapper.toEntity(book));
        booksCache.cachePutUpdateReq(true);
        return mapper.toModel(savedBook);
    }

    public List<BookModel> getAll(int pageN) {
        var booksPageFromCache = booksCache.getBooksPageFromCache(pageN);
        List<BookModel> books = new ArrayList<>();
        Boolean updateLast = booksCache.cacheGetUpdateReq();  // last page update required?
        if (booksPageFromCache.isEmpty()) {  // empty page in cache
            books = getCacheableBooksFromRepo(pageN);
        } else if (booksPageFromCache.size() == pageLen
            || !updateLast) {  // fully filled page or update is not required
            for (Map.Entry<Long, Map.Entry<Integer, BookModel>> entry : booksPageFromCache
                .entrySet()) {  // books from cache
                books.add(entry.getValue()
                    .getValue());
            }
        } else {  // last page update required
            books = getCacheableBooksFromRepo(pageN);
            booksCache.cachePutUpdateReq(false);
        }
        return books;
    }

    public BookModel getOne(long id) {
        var bookFromCache = booksCache.cacheGetBook(id);
        BookModel book;
        if (bookFromCache == null) {
            FencedLock lock = cacheManager.getHazelcastInstance()
                .getCPSubsystem()
                .getLock("id:" + id);
            if (lock.tryLock()) {
                try {
                    book = mapper.toModel(bookRepository.findById(id)
                        .orElseThrow(NoSuchElementException::new));
                    booksCache.cachePutBook(null, book);
                } finally {
                    lock.unlock();
                }
            } else {
                delay(delayGetOne);
                book = booksCache.cacheGetBook(id)
                    .getValue();
            }
        } else {
            book = bookFromCache.getValue();
        }
        return book;
    }

    public long delete(long id) {
        checkElementExist(id);
        bookRepository.deleteById(id);
        Map.Entry<Integer, BookModel> bookFromCache = booksCache.cacheGetBook(id);
        booksCache.evictBook(id);
        if (bookFromCache!=null && bookFromCache.getKey()!=0) {
            int currentPage = bookFromCache.getKey();
            Integer maxPage = booksCache.cacheGetMaxPage();
            if (maxPage != null) {
                for (int page = maxPage; page > currentPage; page--) {
                    var booksPageFromCache = booksCache.getBooksPageFromCache(page);
                    if (!booksPageFromCache.isEmpty()) {
                        booksCache.cachePutBook(page - 1,
                            booksPageFromCache.values()
                                .iterator()
                                .next()
                                .getValue());
                    }
                }
            }
        }
        return id;
    }

    public BookModel update(BookModel book) {
        checkElementExist(book.getId());
        var bookFromCache = booksCache.cacheGetBook(book.getId());
        Integer key = bookFromCache == null ? null : bookFromCache.getKey();
        booksCache.cachePutBook(key, book);
        return mapper.toModel(
            bookRepository.update(
                mapper.toEntity(book)));
    }

    private void checkElementExist(long id) {
        if (bookRepository.findById(id)
            .isEmpty())
            throw new NoSuchElementException();
    }

    private List<BookModel> getCacheableBooksFromRepo(int pageN) {
        FencedLock lock = cacheManager.getHazelcastInstance()
            .getCPSubsystem()
            .getLock("page:" + pageN);
        List<BookModel> books = new ArrayList<>();
        if (lock.tryLock()) {
            try {
                books = mapper.toModelList(
                    bookRepository.findAll(
                            PageRequest.of(pageN - 1, pageLen))
                        .getContent());
                for (BookModel book : books) {
                    booksCache.cachePutBook(pageN, book);
                }
                Integer maxPage = booksCache.cacheGetMaxPage();
                if (books.size() > 0
                    && (maxPage == null || pageN > maxPage)) {
                    booksCache.cachePutMaxPage(pageN);
                }
            } finally {
                lock.unlock();
            }
        } else {
            delay(delayGetAll);
            for (Map.Entry<Long, Map.Entry<Integer, BookModel>> entry : booksCache.getBooksPageFromCache(pageN)
                .entrySet()) {  // books from cache
                books.add(entry.getValue()
                    .getValue());
            }
        }
        return books;
    }

    private void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException interruptedExc) {
            interruptedExc.printStackTrace();
        }
    }
}