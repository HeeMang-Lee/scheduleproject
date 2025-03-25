package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleResponseDto {
    private final long id;
    private final String exerciseDate;
    private final List<String> exercises;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleResponseDto(Long id,String exerciseDate,List<String> exercises,String writer,LocalDateTime createdAt,
                               LocalDateTime modifiedAt) {
        this.id = id;
        this.exerciseDate = exerciseDate;
        this.exercises = exercises;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.exerciseDate = schedule.getExerciseDate();
        this.exercises = schedule.getExercises();
        this.writer = schedule.getWriter();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}
