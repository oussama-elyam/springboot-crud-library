package org.yam.springbootlibrarycrud.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yam.springbootlibrarycrud.dto.BookDto;
import org.yam.springbootlibrarycrud.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto body) {

        BookDto savedBook = bookService.createBook(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/paggination")
    public ResponseEntity<List<BookDto>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BookDto> Books = bookService.getBooks(page, size);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Books.getContent());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto body, @PathVariable("id") Long id) {

        BookDto updatedBook = bookService.updateBook(body, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }
}
