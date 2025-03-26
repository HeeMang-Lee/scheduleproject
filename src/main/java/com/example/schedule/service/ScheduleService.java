package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;
/**
 * 운동 일정(Schedule) 관련 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 */
public interface ScheduleService {
    /**
     * 새로운 운동 일정을 등록합니다.
     *
     * @param requestDto 일정 등록 요청 DTO
     * @return 생성된 일정 응답 DTO
     */
    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);
    /**
     * 전체 운동 일정을 조회합니다.
     * 수정일 또는 작성자 ID 기준으로 필터링이 가능합니다.
     *
     * @param modifiedAt 수정일 (nullable)
     * @param id 작성자 ID (nullable)
     * @return 조건에 맞는 일정 응답 DTO 리스트
     */
    List<ScheduleResponseDto> getAllSchedules(String modifiedAt,Long id);
    /**
     * 단일 운동 일정을 ID로 조회합니다.
     *
     * @param id 일정 ID
     * @return 일정 응답 DTO
     */
    ScheduleResponseDto getSchedule(Long id);
    /**
     * 운동 일정을 수정합니다.
     *
     * @param id 일정 ID
     * @param requestDto 수정할 내용이 담긴 DTO
     * @return 수정된 일정 응답 DTO
     */
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto);
    /**
     * 운동 일정을 삭제합니다.
     * 요청 비밀번호가 기존과 일치해야 삭제됩니다.
     *
     * @param id 일정 ID
     * @param password 요청 시 전달된 비밀번호
     */
    void deleteSchedule(Long id, String password);

}
