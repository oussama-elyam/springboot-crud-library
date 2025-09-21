package org.yam.springbootlibrarycrud.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yam.springbootlibrarycrud.model.Book;
import org.yam.springbootlibrarycrud.dto.BookDto;

@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Book toEntity(BookDto dto) {
        return modelMapper.map(dto, Book.class);
    }

    public BookDto toRequestDto(Book entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    public BookDto toResponseDto(Book entity) {
        return modelMapper.map(entity, BookDto.class);
    }

}

