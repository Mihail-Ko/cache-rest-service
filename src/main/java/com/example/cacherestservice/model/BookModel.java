package com.example.cacherestservice.model;

import com.example.cacherestservice.entity.BookEntity;

import java.util.List;
import java.util.stream.Collectors;

public class BookModel {
    private Long id;
    private String name;
    private String author;
    private String year;

    public static BookModel toModel(BookEntity entity) {
        BookModel model = new BookModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setAuthor(entity.getAuthor());
        model.setYear(entity.getYear());
        return model;
    }

    public BookModel() {
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
