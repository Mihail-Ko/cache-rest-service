package com.example.restservice.mapper;

import com.example.restservice.entity.BookEntity;
import com.example.restservice.model.BookModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookModel toModel(BookEntity entity);

    BookEntity toEntity(BookModel model);

    List<BookModel> toModelList(List<BookEntity> entities);
}