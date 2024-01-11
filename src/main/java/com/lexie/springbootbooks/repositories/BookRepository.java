package com.lexie.springbootbooks.repositories;

import com.lexie.springbootbooks.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
