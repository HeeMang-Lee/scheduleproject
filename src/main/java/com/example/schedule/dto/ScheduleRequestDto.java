package com.example.schedule.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleRequestDto {

    private String exerciseDate;
    private List<String> exercises;
    private String writer;
    private String password;

}
