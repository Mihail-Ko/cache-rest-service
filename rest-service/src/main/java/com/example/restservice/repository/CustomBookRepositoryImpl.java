package com.example.restservice.repository;

import com.example.restservice.entity.BookEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository {
    private final BookRepository bookRepository;

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

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        delay(delayGetAll);
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findById(long id) {
        delay(delayGetOne);
        return bookRepository.findById(id);
    }

    @Override
    public BookEntity save(BookEntity entity) {
        return bookRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
        delay(delayDelete);
        bookRepository.deleteById(id);
    }

    private void delay(int delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException interruptedExc) {
            interruptedExc.printStackTrace();
        }
    }
}
