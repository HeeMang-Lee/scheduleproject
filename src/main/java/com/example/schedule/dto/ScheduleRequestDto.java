package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "운동 부위 선택은 필수 입력 항목입니다.")
    private String exerciseDate;

    @NotEmpty(message = "운동 목록은 최소 1개 이상이어야 합니다.")
    private List<String> exercises;

    @NotBlank(message = "작성자 ID는 필수 입력 항목입니다.")
    private Long authorId;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

}
