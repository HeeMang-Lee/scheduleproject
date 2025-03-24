package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleResponseDto {
    private long id;
    private String exerciseDate;
    private List<String> exercises;
    private String writer;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Long id, String exerciseDate, List<String> exercises,
                               String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.exerciseDate = exerciseDate;
        this.exercises = exercises;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
