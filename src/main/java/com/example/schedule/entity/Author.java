package com.example.schedule.entity;

import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class Author {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public Author(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Author(String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this(null,name,email,createdAt,modifiedAt);
    }
}
