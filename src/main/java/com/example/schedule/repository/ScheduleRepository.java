package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto save(Schedule schedule);
    List<ScheduleResponseDto> findAll(String modifiedAt, Long authorId);
    Optional<ScheduleResponseDto> findById(Long id);
    Optional<Schedule> findEntityById(Long id);
    void update(Schedule schedule);
    Optional<Schedule> findByIdForDelete(Long id);
    void delete(Long id);
}
