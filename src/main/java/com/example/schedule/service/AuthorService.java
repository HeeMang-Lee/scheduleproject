package com.example.schedule.service;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto requestDto);
    AuthorResponseDto getAutorById(Long id);
}
