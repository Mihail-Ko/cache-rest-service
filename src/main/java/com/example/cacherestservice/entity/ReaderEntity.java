package com.example.cacherestservice.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "reader")
public class ReaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String secondName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reader")
    private List<BorrowingEntity> borrowings;

    public List<BorrowingEntity> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<BorrowingEntity> borrowings) {
        this.borrowings = borrowings;
    }

    public ReaderEntity() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
