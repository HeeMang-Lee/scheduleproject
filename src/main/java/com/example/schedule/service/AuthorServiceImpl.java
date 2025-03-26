package com.example.schedule.service;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
/**
 * {@link AuthorService} 구현체로 작성자 등록 및 조회 로직을 처리합니다.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    /**
     * 생성자
     *
     * @param authorRepository 작성자 저장소
     */
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    /**
     * 작성자 등록을 처리합니다.
     * 이메일 중복을 검사하고, 중복 시 400 예외를 반환합니다.
     *
     * @param requestDto 작성자 등록 요청 DTO
     * @return 생성된 작성자 응답 DTO
     */
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
    /**
     * ID를 기반으로 작성자 정보를 조회합니다.
     * 존재하지 않을 경우 404 예외를 발생시킵니다.
     *
     * @param id 조회할 작성자 ID
     * @return 조회된 작성자 응답 DTO
     */
    @Override
    public AuthorResponseDto getAutorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"작성자를 찾을 수 없습니다."));
        return new AuthorResponseDto(author);
    }
}
