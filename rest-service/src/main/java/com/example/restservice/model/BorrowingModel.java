package com.example.restservice.model;

import com.example.restservice.entity.BorrowingEntity;

public class BorrowingModel {
    private Long id;
    private Boolean returned;
    private String borrowingDate;
    private Long bookId;
    private Long readerId;

    public static BorrowingModel toModel(BorrowingEntity entity) {
        BorrowingModel model = new BorrowingModel();
        model.setId(entity.getId());
        model.setReturned(entity.getReturned());
        model.setBorrowingDate(entity.getBorrowingDate());
        model.setBookId(entity.getBook().getId());
        model.setReaderId(entity.getReader().getId());
        return model;
    }

    public BorrowingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    public String getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(String borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }
}
