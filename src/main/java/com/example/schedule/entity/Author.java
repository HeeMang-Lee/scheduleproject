package com.example.schedule.entity;

import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

/**
 * 작성자 정보를 나타내는 엔티티 클래스입니다.
 * 일정(Schedule)과 연관되어 사용됩니다.
 */
@Getter
public class Author {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    /**
     * 모든 필드를 포함하는 생성자
     *
     * @param id 작성자 ID
     * @param name 이름
     * @param email 이메일
     * @param createdAt 등록일시
     * @param modifiedAt 수정일시
     */
    public Author(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    /**
     * ID 없이 생성자를 호출할 때 사용되는 생성자
     *
     * @param name 이름
     * @param email 이메일
     * @param createdAt 등록일시
     * @param modifiedAt 수정일시
     */
    public Author(String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this(null,name,email,createdAt,modifiedAt);
    }
}
