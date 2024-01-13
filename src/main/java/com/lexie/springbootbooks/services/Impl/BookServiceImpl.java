package com.lexie.springbootbooks.services.Impl;

import com.lexie.springbootbooks.domain.entities.BookEntity;
import com.lexie.springbootbooks.repositories.BookRepository;
import com.lexie.springbootbooks.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
