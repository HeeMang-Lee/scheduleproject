package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(requestDto.getExerciseDate(),requestDto.getExercises(),requestDto.getWriter(),
                requestDto.getPassword(),now,now ) ;

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(String modifiedAt, String writer) {
        return scheduleRepository.findAll(modifiedAt,writer);
    }
}
