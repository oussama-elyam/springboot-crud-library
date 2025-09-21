package org.yam.springbootlibrarycrud.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootlibrarycrud.dto.BookDto;
import org.yam.springbootlibrarycrud.common.exception.ResourceConflictException;
import org.yam.springbootlibrarycrud.mapper.BookMapper;
import org.yam.springbootlibrarycrud.model.Book;
import org.yam.springbootlibrarycrud.model.StatusBook;
import org.yam.springbootlibrarycrud.repository.BookRepository;
import org.yam.springbootlibrarycrud.service.BookService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Transactional
    @Override
    public BookDto createBook(BookDto bodyRequest) {
        Book body = bookMapper.toEntity(bodyRequest);


        if (bookRepository.existsByName(body.getName())) {
            throw new ResourceConflictException("Book name must be unique. The name '" + body.getName() + "' already exists.");
        }

        Book newBook = Book.builder()
                .name(body.getName())
                .price(body.getPrice())
                .qte(body.getQte())
                .statusBook((body.getStatusBook() == null && body.getQte() > 0)
                        ? StatusBook.AVAILABLE
                        : StatusBook.NOTAVAILABLE)
                .build();

        return bookMapper.toResponseDto(bookRepository.save(newBook));
    }

    @Override
    public Page<BookDto> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.map(bookMapper::toResponseDto);
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bodyRequest, Long id) {
        Book body = bookMapper.toEntity(bodyRequest);
        body.setId(id); // Ensure the id remains unchanged

        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            logger.warn("Book with id {} not found", id);
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }

        body.setStatusBook(body.getQte() > 0 ? StatusBook.AVAILABLE : StatusBook.NOTAVAILABLE);

        existingBook.setName(body.getName());
        existingBook.setPrice(body.getPrice());
        existingBook.setQte(body.getQte());
        existingBook.setStatusBook(body.getStatusBook());

        return bookMapper.toResponseDto(bookRepository.save(body));
    }

    @Override
    public void deleteBook(Long id) {

        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            logger.warn("Book with id {} not found", id);
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
