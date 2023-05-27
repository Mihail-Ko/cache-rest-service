package com.example.restservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookModel {
    private long id;
    private String name;
    private String author;
    private String year;
}