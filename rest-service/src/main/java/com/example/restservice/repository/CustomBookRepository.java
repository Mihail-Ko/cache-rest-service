package com.example.restservice.repository;

import com.example.restservice.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomBookRepository {
    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findById(long id);

    BookEntity save(BookEntity entity);

    void deleteById(long id);
}
