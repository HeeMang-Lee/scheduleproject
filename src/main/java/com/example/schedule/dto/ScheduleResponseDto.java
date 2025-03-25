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
    private final long authorId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleResponseDto(Long id,String exerciseDate,List<String> exercises,long authorId,LocalDateTime createdAt,
                               LocalDateTime modifiedAt) {
        this.id = id;
        this.exerciseDate = exerciseDate;
        this.exercises = exercises;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.exerciseDate = schedule.getExerciseDate();
        this.exercises = schedule.getExercises();
        this.authorId = schedule.getAuthorId();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}
