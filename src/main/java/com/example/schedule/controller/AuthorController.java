package com.example.schedule.controller;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * REST 컨트롤러 클래스 - 작성자(Author) 관련 API를 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * 작성자 등록 API
     *
     * @param requestDto 작성자 등록 요청 데이터 (이름, 이메일, 비밀번호 포함)
     * @return 생성된 작성자 정보를 포함한 응답 (HTTP 201 Created)
     */
    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto requestDto) {
        AuthorResponseDto responseDto = authorService.createAuthor(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    /**
     * 작성자 단건 조회 API
     *
     * @param id 조회할 작성자의 ID
     * @return 조회된 작성자 정보 (HTTP 200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable Long id) {
        AuthorResponseDto responseDto = authorService.getAutorById(id);
        return ResponseEntity.ok(responseDto);
    }
}
