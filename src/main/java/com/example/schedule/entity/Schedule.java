package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Schedule {
    private  Long id;
    private  String exerciseDate;
    private  List<String> exercises;
    private  String writer;
    private  String password;
    private  LocalDateTime createdAt;
    private  LocalDateTime modifiedAt;

    public Schedule(String exerciseDate, List<String> exercises,String writer,String password,
                    LocalDateTime createdAt,LocalDateTime modifiedAt) {
        this.exerciseDate = exerciseDate;
        this.exercises = exercises;
        this.writer = writer;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
