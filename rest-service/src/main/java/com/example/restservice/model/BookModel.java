package com.example.restservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BookModel implements Serializable {
    private long id;
    private String name;
    private String author;
    private String year;
}