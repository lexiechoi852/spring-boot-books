package com.lexie.springbootbooks.controllers;

import com.lexie.springbootbooks.domain.dto.BookDto;
import com.lexie.springbootbooks.domain.entities.BookEntity;
import com.lexie.springbootbooks.mappers.Mapper;
import com.lexie.springbootbooks.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
    private Mapper<BookEntity, BookDto> bookMapper;

    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDto> createOrUpdateBook(@PathVariable String isbn, @RequestBody BookDto book) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createOrUpdateBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        if(bookExists){
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }
    }

    @GetMapping
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
        Optional<BookEntity> book = bookService.findOne(isbn);
        return book.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(@PathVariable String isbn, @RequestBody BookDto book) {

        if(!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(book);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        return new ResponseEntity<>(
                bookMapper.mapTo(updatedBookEntity),
                HttpStatus.OK
        );
    }
}
