package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 운동 일정이 존재하지 않습니다.")
        );
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule existing = scheduleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 운동 일정이 존재하지 않습니다.")
        );
        if (!existing.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        Schedule updatedSchedule = new Schedule(
                id,
                requestDto.getExerciseDate(),
                requestDto.getExercises(),
                requestDto.getWriter(),
                existing.getPassword(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );

        scheduleRepository.update(id, updatedSchedule);
        return new ScheduleResponseDto((updatedSchedule));
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule existing = scheduleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 운동 일정이 존재하지 않습니다.")
        );
        if (!existing.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(id);
    }
}
