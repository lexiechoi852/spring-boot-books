package com.lexie.springbootbooks.services.Impl;

import com.lexie.springbootbooks.domain.entities.AuthorEntity;
import com.lexie.springbootbooks.repositories.AuthorRepository;
import com.lexie.springbootbooks.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}