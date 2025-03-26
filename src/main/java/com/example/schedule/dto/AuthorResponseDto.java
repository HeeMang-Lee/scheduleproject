package com.example.schedule.dto;

import com.example.schedule.entity.Author;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class AuthorResponseDto {
    private final Long id;
    private final String name;
    private final String email;


    public AuthorResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
    }
}
