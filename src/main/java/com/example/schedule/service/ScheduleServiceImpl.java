package com.example.schedule.service;

import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.AuthorRepository;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {
        Author author = authorRepository.findById(requestDto.getAuthorId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"유효하지 않은 작성자 ID입니다."));

        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(
                requestDto.getAuthorId(),
                requestDto.getExerciseDate(),
                requestDto.getExercises(),
                requestDto.getPassword(),
                now,now
        );

        ScheduleResponseDto savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getExerciseDate(),
                savedSchedule.getExercises(),
                new AuthorResponseDto(author),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(String modifiedAt, Long authorId) {
//        List<ScheduleResponseDto> schedules = scheduleRepository.findAll(modifiedAt, authorId);
//        List<ScheduleResponseDto> result = new ArrayList<>();
//
//        for (ScheduleResponseDto dto : schedules) {
//            Author author = authorRepository.findById(dto.getAuthorId())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"작성자 정보를 찾을 수 없습니다."));
//
//            result.add(new ScheduleResponseDto(
//                    dto.getId(),
//                    dto.getExerciseDate(),
//                    dto.getExercises(),
//                    new AuthorResponseDto(author),
//                    dto.getCreatedAt(),
//                    dto.getModifiedAt()
//            ));
//        }
        return scheduleRepository.findAll(modifiedAt, authorId);
    }

    @Override
    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 운동 일정이 존재하지 않습니다."));
    }


    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule existing = scheduleRepository.findEntityById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 운동 일정이 존재하지 않습니다."));

        if (!existing.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        Author author = authorRepository.findById(requestDto.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"유효하지 않은 작성자 ID입니다."));

        Schedule updated = new Schedule(
                existing.getId(),
                requestDto.getAuthorId(),
                requestDto.getExerciseDate(),
                requestDto.getExercises(),
                existing.getPassword(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );

        scheduleRepository.update(updated);

        return new ScheduleResponseDto(
                updated.getId(),
                updated.getExerciseDate(),
                updated.getExercises(),
                new AuthorResponseDto(author),
                updated.getCreatedAt(),
                updated.getModifiedAt()
        );
    }


    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule existing = scheduleRepository.findByIdForDelete(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 운동 일정이 존재하지 않습니다."));

        if (!existing.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(id);
    }
}
