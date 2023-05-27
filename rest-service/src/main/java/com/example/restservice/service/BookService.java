package com.example.restservice.service;

import com.example.restservice.mapper.BookMapper;
import com.example.restservice.model.BookModel;
import com.example.restservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    @Value("${delay.getOne}")
    private int delayGetOne;
    @Value("${delay.getAll}")
    private int delayGetAll;
    @Value("${delay.delete}")
    private int delayDelete;
    @Value("${delay.update}")
    private int delayUpdate;
    @Value("${delay.add}")
    private int delayAdd;

    public BookModel addBook(BookModel book) {
        delay(delayAdd);
        return mapper.toModel(
            bookRepository.save(
                mapper.toEntity(book)
            )
        );
    }

    public List<BookModel> getAll(int pageN) {
        delay(delayGetAll);
        return mapper.toModelList(
            bookRepository.findAll(
                    PageRequest.of(pageN - 1, 10)
                )
                .getContent()
        );
    }

    public BookModel getOne(long id) {
        delay(delayGetOne);
        return mapper.toModel(
            bookRepository.findById(id)
                .orElseThrow(NoSuchElementException::new)
        );
    }

    public long delete(long id) {
        delay(delayDelete);
        checkElementExist(id);
        bookRepository.deleteById(id);
        return id;
    }

    public BookModel update(BookModel updatedBook) {
        delay(delayUpdate);
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

    private void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException interruptedExc) {
            interruptedExc.printStackTrace();
        }
    }
}
