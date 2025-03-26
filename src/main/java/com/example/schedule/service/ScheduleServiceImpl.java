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
/**
 * {@link ScheduleService} 구현체로, 운동 일정 생성, 조회, 수정, 삭제 등의 비즈니스 로직을 담당합니다.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthorRepository authorRepository;
    /**
     * 생성자
     *
     * @param scheduleRepository 일정 저장소
     * @param authorRepository   작성자 저장소
     */
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.authorRepository = authorRepository;
    }
    /**
     * 새로운 운동 일정을 저장합니다.
     * 작성자 ID 유효성 검사를 수행하며, 저장 후 DTO 형태로 반환합니다.
     *
     * @param requestDto 일정 등록 요청 DTO
     * @return 생성된 일정 응답 DTO
     */
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
    /**
     * 조건에 맞는 전체 운동 일정을 조회합니다.
     *
     * @param modifiedAt 수정일 필터 (nullable)
     * @param authorId   작성자 ID 필터 (nullable)
     * @return 조건에 부합하는 일정 응답 DTO 리스트
     */
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
    /**
     * ID를 기반으로 단일 운동 일정을 조회합니다.
     *
     * @param id 일정 ID
     * @return 조회된 일정 응답 DTO
     */
    @Override
    public ScheduleResponseDto getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 운동 일정이 존재하지 않습니다."));
    }

    /**
     * 운동 일정을 수정합니다.
     * 비밀번호 검증과 작성자 ID 유효성 검사를 포함합니다.
     *
     * @param id         일정 ID
     * @param requestDto 수정 요청 DTO
     * @return 수정된 일정 응답 DTO
     */
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

    /**
     * 운동 일정을 삭제합니다.
     * 비밀번호가 일치해야 삭제가 가능합니다.
     *
     * @param id       일정 ID
     * @param password 요청 시 전달된 비밀번호
     */
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
