package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * REST 컨트롤러 클래스 - 운동 일정(Schedule) 관련 API를 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 운동 일정 생성 API
     *
     * @param requestDto 운동 일정 등록 요청 데이터
     * @return 생성된 운동 일정 정보 (HTTP 201 Created)
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }
    /**
     * 운동 일정 전체 조회 API
     *
     * @param modifiedAt 수정일 필터 (선택)
     * @param authorId   작성자 ID 필터 (선택)
     * @return 조건에 맞는 운동 일정 리스트 (HTTP 200 OK)
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) String modifiedAt,
            @RequestParam(required = false) Long authorId
    ) {
        List<ScheduleResponseDto> response = scheduleService.getAllSchedules(modifiedAt,authorId);
        return ResponseEntity.ok(response);
    }
    /**
     * 단일 운동 일정 조회 API
     *
     * @param id 조회할 일정 ID
     * @return 조회된 일정 정보 (HTTP 200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto response = scheduleService.getSchedule(id);
        return ResponseEntity.ok(response);
    }
    /**
     * 운동 일정 수정 API
     *
     * @param id         수정할 일정 ID
     * @param requestDto 수정 요청 데이터
     * @return 수정된 일정 정보 (HTTP 200 OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleRequestDto requestDto
    ) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id,requestDto);
        return ResponseEntity.ok(responseDto);
    }
    /**
     * 운동 일정 삭제 API
     *
     * @param id   삭제할 일정 ID
     * @param body 요청 본문에 포함된 비밀번호 정보
     * @return HTTP 204 No Content (삭제 성공)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody @Valid Map<String, String> body
    ) {
        String password = body.get("password");
        scheduleService.deleteSchedule(id,password);
        return ResponseEntity.noContent().build();
    }

}
