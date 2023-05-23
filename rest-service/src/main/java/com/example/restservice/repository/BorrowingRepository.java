package com.example.restservice.repository;

import com.example.restservice.entity.BorrowingEntity;
import org.springframework.data.repository.CrudRepository;

public interface BorrowingRepository extends CrudRepository<BorrowingEntity, Long> {
}
