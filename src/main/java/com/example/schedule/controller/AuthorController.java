package com.example.schedule.controller;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto requestDto) {
        AuthorResponseDto responseDto = authorService.createAuthor(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable Long id) {
        AuthorResponseDto responseDto = authorService.getAutorById(id);
        return ResponseEntity.ok(responseDto);
    }
}
