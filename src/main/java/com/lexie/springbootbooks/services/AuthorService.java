package com.lexie.springbootbooks.services;

import com.lexie.springbootbooks.domain.entities.AuthorEntity;

import java.util.Optional;

public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);
}
