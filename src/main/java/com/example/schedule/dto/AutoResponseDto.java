package com.example.schedule.dto;

import com.example.schedule.entity.Author;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class AutoResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public AutoResponseDto(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public AutoResponseDto(Author author) {
        this(author.getId(), author.getName(), author.getEmail(), author.getCreatedAt(),author.getModifiedAt());
    }
}
