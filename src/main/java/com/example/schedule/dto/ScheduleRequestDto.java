package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
/**
 * 운동 일정 등록 및 수정 요청을 위한 DTO 클래스입니다.
 * 운동 부위, 운동 목록, 작성자 ID, 비밀번호를 입력받습니다.
 */
@Getter
public class ScheduleRequestDto {
    /**
     * 운동 부위 또는 주제 (예: 가슴 운동, 등 운동)
     */
    @NotBlank(message = "운동 부위 선택은 필수 입력 항목입니다.")
    private String exerciseDate;
    /**
     * 운동 항목 목록 (최소 1개 이상 필수)
     */
    @NotEmpty(message = "운동 목록은 최소 1개 이상이어야 합니다.")
    private List<String> exercises;
    /**
     * 작성자 ID (Author 엔티티의 외래키)
     */
    @NotNull(message = "작성자 ID는 필수 입력 항목입니다.")
    private Long authorId;
    /**
     * 작성 시 입력한 비밀번호 (수정 및 삭제 시 검증용)
     */
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

}
