package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 운동 일정을 나타내는 엔티티 클래스입니다.
 * 작성자(Author)와 연관되며, 운동 부위, 항목, 비밀번호, 생성/수정일을 포함합니다.
 */
@Getter
public class Schedule {
    private final Long id;
    private final Long authorId;
    private  final String exerciseDate;
    private  final List<String> exercises;
    private  final String password;
    private  final LocalDateTime createdAt;
    private  final LocalDateTime modifiedAt;
    /**
     * 전체 필드를 초기화하는 생성자
     *
     * @param id 일정 ID
     * @param authorId 작성자 ID
     * @param exerciseDate 운동 주제
     * @param exercises 운동 항목 목록
     * @param password 비밀번호
     * @param createdAt 생성일시
     * @param modifiedAt 수정일시
     */
    public Schedule(Long id,Long authorId,String exerciseDate,List<String> exercises,String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.authorId = authorId;
        this.exercises = exercises;
        this.exerciseDate = exerciseDate;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    /**
     * ID 없이 일정 생성 시 사용하는 생성자
     *
     * @param authorId 작성자 ID
     * @param exerciseDate 운동 주제
     * @param exercises 운동 항목 목록
     * @param password 비밀번호
     * @param createdAt 생성일시
     * @param modifiedAt 수정일시
     */
    public Schedule(Long authorId,String exerciseDate, List<String> exercises,String password,
                    LocalDateTime createdAt,LocalDateTime modifiedAt) {
       this(null,authorId,exerciseDate,exercises,password,createdAt,modifiedAt);
    }
}
