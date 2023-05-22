package com.example.cacherestservice.mapper;

import com.example.cacherestservice.entity.BookEntity;
import com.example.cacherestservice.model.BookModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookModel toModel(BookEntity entity);

    List<BookModel> toModelList(List<BookEntity> entities);
}
