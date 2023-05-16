package com.example.cacherestservice.repository;

import com.example.cacherestservice.entity.BorrowingEntity;
import org.springframework.data.repository.CrudRepository;

public interface BorrowingRepository extends CrudRepository<BorrowingEntity, Long> {
}
