package com.example.schedule.dto;

import com.example.schedule.entity.Author;
import lombok.Getter;

/**
 * 작성자 정보 응답을 위한 DTO 클래스입니다.
 * 클라이언트에게 작성자의 ID, 이름, 이메일 정보를 제공합니다.
 */
@Getter
public class AuthorResponseDto {

    private final Long id;
    private final String name;
    private final String email;

    /**
     * 생성자 - 개별 필드로 초기화
     *
     * @param id 작성자 ID
     * @param name 작성자 이름
     * @param email 작성자 이메일
     */
    public AuthorResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }
    /**
     * 생성자 - Author 엔티티로부터 초기화
     *
     * @param author Author 엔티티 객체
     */
    public AuthorResponseDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.email = author.getEmail();
    }
}
