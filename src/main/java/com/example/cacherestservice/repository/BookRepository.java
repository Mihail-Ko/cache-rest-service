package com.example.cacherestservice.repository;

import com.example.cacherestservice.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {
}