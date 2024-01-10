package com.lexie.springbootjdbc.dao.impl;

import com.lexie.springbootjdbc.dao.BookDao;
import com.lexie.springbootjdbc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;

public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(),book.getTitle(), book.getAuthorId()
        );
    }
}
