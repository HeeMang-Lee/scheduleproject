package com.example.schedule.dto;

import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
/**
 * 운동 일정 응답을 위한 DTO 클래스입니다.
 * 클라이언트에게 일정 상세 정보 및 작성자 정보를 제공합니다.
 */
@Getter
public class ScheduleResponseDto {
    private final long id;
    private final String exerciseDate;
    private final List<String> exercises;
    private final AuthorResponseDto author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    /**
     * 생성자 - 모든 필드를 초기화
     *
     * @param id 일정 ID
     * @param exerciseDate 운동 주제
     * @param exercises 운동 항목 리스트
     * @param author 작성자 정보 DTO
     * @param createdAt 생성 시각
     * @param modifiedAt 수정 시각
     */
    public ScheduleResponseDto(Long id,String exerciseDate,List<String> exercises,AuthorResponseDto author,LocalDateTime createdAt,
                               LocalDateTime modifiedAt) {
        this.id = id;
        this.exerciseDate = exerciseDate;
        this.exercises = exercises;
        this.author = author;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
