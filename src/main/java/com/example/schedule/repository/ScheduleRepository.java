package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto save(Schedule schedule);
    List<ScheduleResponseDto> findAll(String modifiedAt, String writer);
    Optional<Schedule> findById(Long id);
    void update(Long id, Schedule schedule);
    void delete(Long id);
}
