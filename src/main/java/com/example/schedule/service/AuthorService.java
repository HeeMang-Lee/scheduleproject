package com.example.schedule.service;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
/**
 * 작성자(Author) 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 */
public interface AuthorService {
    /**
     * 작성자 등록 요청을 처리합니다.
     *
     * @param requestDto 작성자 등록 요청 DTO
     * @return 등록된 작성자 정보 응답 DTO
     */
    AuthorResponseDto createAuthor(AuthorRequestDto requestDto);
    /**
     * ID로 작성자 정보를 조회합니다.
     *
     * @param id 작성자 ID
     * @return 조회된 작성자 정보 응답 DTO
     */
    AuthorResponseDto getAutorById(Long id);
}
