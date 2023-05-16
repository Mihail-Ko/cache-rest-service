package com.example.cacherestservice.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private String year;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<BorrowingEntity> borrowings;

    public List<BorrowingEntity> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<BorrowingEntity> borrowings) {
        this.borrowings = borrowings;
    }

    public BookEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
