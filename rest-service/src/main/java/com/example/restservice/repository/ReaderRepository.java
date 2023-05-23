package com.example.restservice.repository;

import com.example.restservice.entity.ReaderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<ReaderEntity, Long> {
}
