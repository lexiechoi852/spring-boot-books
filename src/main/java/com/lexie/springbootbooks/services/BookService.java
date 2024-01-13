package com.lexie.springbootbooks.services;

import com.lexie.springbootbooks.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity book);
}
