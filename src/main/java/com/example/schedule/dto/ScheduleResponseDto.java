package com.example.schedule.dto;

import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleResponseDto {
    private final long id;
    private final String exerciseDate;
    private final List<String> exercises;
    private final AuthorResponseDto author;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


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
