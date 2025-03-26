package com.example.schedule.repository;

import com.example.schedule.entity.Author;

import java.util.Optional;
/**
 * 작성자(Author) 정보를 저장하고 조회하기 위한 Repository 인터페이스입니다.
 * 구현체에서는 JDBC를 이용하여 실제 데이터베이스 연동을 수행합니다.
 */
public interface AuthorRepository {

    /**
     * 작성자 정보를 저장합니다.
     *
     * @param author 저장할 작성자 엔티티
     * @return 저장된 작성자 엔티티
     */
    Author save(Author author);
    /**
     * 작성자 ID로 작성자 정보를 조회합니다.
     *
     * @param id 작성자 ID
     * @return 조회된 작성자 정보 (없으면 빈 Optional)
     */
    Optional<Author> findById(Long id);
    /**
     * 이메일로 작성자 정보를 조회합니다.
     *
     * @param email 작성자 이메일
     * @return 조회된 작성자 정보 (없으면 빈 Optional)
     */
    Optional<Author> findByEmail(String email);
}
