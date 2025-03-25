package com.example.schedule.repository;

import com.example.schedule.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);
    Optional<Author> findById(Long id);
    Optional<Author> findByEmail(String email);
}
