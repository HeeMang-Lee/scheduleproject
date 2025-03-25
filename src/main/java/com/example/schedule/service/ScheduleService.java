package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    List<ScheduleResponseDto> getAllSchedules(String modifiedAt,String writer);
    ScheduleResponseDto getSchedule(Long id);
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);

}
