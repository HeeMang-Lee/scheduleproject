package com.example.schedule.service;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto requestDto) {
        if (authorRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 등록된 이메일입니다.");
        }
        LocalDateTime now = LocalDateTime.now();

        Author author = new Author(
                requestDto.getName(),
                requestDto.getEmail(),
                now,
                now
        );

        Author savedAuthor = authorRepository.save(author);
        return new AuthorResponseDto(savedAuthor);
    }

    @Override
    public AuthorResponseDto getAutorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"작성자를 찾을 수 없습니다."));
        return new AuthorResponseDto(author);
    }
}
