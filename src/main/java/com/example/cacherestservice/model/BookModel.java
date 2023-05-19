package com.example.cacherestservice.model;

import com.example.cacherestservice.entity.BookEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookModel {

    private Long id;

    private String name;

    private String author;

    private String year;

    public static BookModel toModel(BookEntity entity) {
        return BookModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .author(entity.getAuthor())
                .year(entity.getYear())
                .build();
    }

}
