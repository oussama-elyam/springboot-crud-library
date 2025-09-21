package org.yam.springbootlibrarycrud.service;

import org.springframework.data.domain.Page;
import org.yam.springbootlibrarycrud.dto.BookDto;

public interface BookService {
    BookDto createBook(BookDto body);

    Page<BookDto> getBooks(int page, int size);

    BookDto updateBook(BookDto body, Long id);

    void deleteBook(Long id);
}
