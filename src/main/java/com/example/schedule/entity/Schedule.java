package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Schedule {
    private final Long id;
    private final Long authorId;
    private  final String exerciseDate;
    private  final List<String> exercises;
    private  final String password;
    private  final LocalDateTime createdAt;
    private  final LocalDateTime modifiedAt;

    public Schedule(Long id,Long authorId,String exerciseDate,List<String> exercises,String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.authorId = authorId;
        this.exercises = exercises;
        this.exerciseDate = exerciseDate;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Schedule(Long authorId,String exerciseDate, List<String> exercises,String password,
                    LocalDateTime createdAt,LocalDateTime modifiedAt) {
       this(null,authorId,exerciseDate,exercises,password,createdAt,modifiedAt);
    }
}
