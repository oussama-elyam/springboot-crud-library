package org.yam.springbootlibrarycrud.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yam.springbootlibrarycrud.common.exception.ResourceConflictException;
import org.yam.springbootlibrarycrud.dto.BookDto;
import org.yam.springbootlibrarycrud.mapper.BookMapper;
import org.yam.springbootlibrarycrud.entitie.Book;
import org.yam.springbootlibrarycrud.entitie.enums.StatusBook;
import org.yam.springbootlibrarycrud.repository.BookRepository;
import org.yam.springbootlibrarycrud.service.BookService;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Transactional
    @Override
    public BookDto createBook(BookDto bodyRequest) {
        if (bookRepository.existsByName(bodyRequest.getName())) {
            throw new ResourceConflictException(
                    "Book name must be unique. The name '" + bodyRequest.getName() + "' already exists."
            );
        }

        Book newBook = Book.builder()
                .name(bodyRequest.getName())
                .price(bodyRequest.getPrice())
                .qte(bodyRequest.getQte())
                .statusBook(resolveStatus(bodyRequest.getQte()))
                .build();

        return bookMapper.toResponseDto(bookRepository.save(newBook));
    }

    @Override
    public Page<BookDto> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable).map(bookMapper::toResponseDto);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bodyRequest, Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found"));

        existingBook.setName(bodyRequest.getName());
        existingBook.setPrice(bodyRequest.getPrice());
        existingBook.setQte(bodyRequest.getQte());
        existingBook.setStatusBook(resolveStatus(bodyRequest.getQte()));

        return bookMapper.toResponseDto(existingBook); // no explicit save() needed, managed entity
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book with ID " + id + " not found")
                );

        bookRepository.delete(existingBook);
    }

    private StatusBook resolveStatus(Integer qte) {
        return qte > 0 ? StatusBook.AVAILABLE : StatusBook.NOTAVAILABLE;
    }
}
