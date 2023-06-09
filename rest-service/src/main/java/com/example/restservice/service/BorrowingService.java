package com.example.restservice.service;

import com.example.restservice.entity.BookEntity;
import com.example.restservice.entity.BorrowingEntity;
import com.example.restservice.entity.ReaderEntity;
import com.example.restservice.model.BorrowingModel;
import com.example.restservice.repository.BookRepository;
import com.example.restservice.repository.BorrowingRepository;
import com.example.restservice.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;

    public List<BorrowingModel> getAll() {
        List<BorrowingEntity> entitiesList = (List<BorrowingEntity>) borrowingRepository.findAll();
        return entitiesList
            .stream()
            .map(BorrowingModel::toModel)
            .collect(Collectors.toList());
    }

    public BorrowingModel getOne(Long id) {
        BorrowingEntity borrowing = borrowingRepository.findById(id).get();
        return BorrowingModel.toModel(borrowing);
    }

    public BorrowingModel addBorrowing(BorrowingModel borrowing) {
        BorrowingEntity borrowingEntity = new BorrowingEntity();
        BookEntity book = bookRepository.findById(borrowing.getBookId()).get();
        ReaderEntity reader = readerRepository.findById(borrowing.getReaderId()).get();
        borrowingEntity.setBook(book);
        borrowingEntity.setReader(reader);
        borrowingEntity.setReturned(false);
        borrowingEntity.setBorrowingDate(borrowing.getBorrowingDate());
        return BorrowingModel.toModel(borrowingRepository.save(borrowingEntity));
    }

    public Long delete(Long id) {
        borrowingRepository.findById(id).get();
        borrowingRepository.deleteById(id);
        return id;
    }

    public BorrowingModel update(Long id) {
        BorrowingEntity borrowing = borrowingRepository.findById(id).get();
        borrowing.setReturned(!borrowing.getReturned());
        return BorrowingModel.toModel(borrowingRepository.save(borrowing));
    }
}