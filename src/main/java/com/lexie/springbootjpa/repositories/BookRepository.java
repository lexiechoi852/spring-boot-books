package com.lexie.springbootjpa.repositories;

import com.lexie.springbootjpa.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
