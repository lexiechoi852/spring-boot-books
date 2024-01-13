package com.lexie.springbootbooks.services;

import com.lexie.springbootbooks.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
