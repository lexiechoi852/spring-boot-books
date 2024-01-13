package com.lexie.springbootbooks.repositories;

import com.lexie.springbootbooks.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("SELECT a from AuthorEntity a WHERE a.age > ?1")
    Iterable<AuthorEntity> findAgeGreaterThan(int age);
}
