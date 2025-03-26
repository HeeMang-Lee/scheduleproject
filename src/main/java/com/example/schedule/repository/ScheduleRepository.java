package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;
/**
 * 운동 일정(Schedule) 관련 데이터베이스 접근을 위한 Repository 인터페이스입니다.
 * 일정 저장, 조회, 수정, 삭제 기능을 정의합니다.
 */
public interface ScheduleRepository {
    /**
     * 일정 정보를 저장합니다.
     *
     * @param schedule 저장할 일정 엔티티
     * @return 저장된 일정 정보에 대한 응답 DTO
     */
    ScheduleResponseDto save(Schedule schedule);
    /**
     * 전체 일정 목록을 조건에 따라 조회합니다.
     *
     * @param modifiedAt 수정일 필터 (nullable)
     * @param authorId 작성자 ID 필터 (nullable)
     * @return 조건에 부합하는 일정 응답 DTO 리스트
     */
    List<ScheduleResponseDto> findAll(String modifiedAt, Long authorId);
    /**
     * 일정 ID로 일정 정보를 조회합니다 (응답용 DTO 반환).
     *
     * @param id 일정 ID
     * @return 조회된 일정 DTO (Optional)
     */
    Optional<ScheduleResponseDto> findById(Long id);
    /**
     * 일정 ID로 일정 정보를 조회합니다 (엔티티 반환).
     *
     * @param id 일정 ID
     * @return 조회된 일정 엔티티 (Optional)
     */
    Optional<Schedule> findEntityById(Long id);
    /**
     * 일정 정보를 수정합니다.
     *
     * @param schedule 수정할 일정 엔티티
     */
    void update(Schedule schedule);
    /**
     * 삭제를 위한 일정 조회 (비밀번호 포함 검증용).
     *
     * @param id 일정 ID
     * @return 삭제 대상 일정 (Optional)
     */
    Optional<Schedule> findByIdForDelete(Long id);
    /**
     * 일정 ID로 일정을 삭제합니다.
     *
     * @param id 삭제할 일정 ID
     */
    void delete(Long id);
}
