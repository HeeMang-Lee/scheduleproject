package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
/**
 * 작성자 등록 요청을 처리하기 위한 DTO 클래스입니다.
 * 사용자로부터 이름과 이메일을 입력받습니다.
 */
@Getter
public class AuthorRequestDto {
    /**
     * 작성자 이름 (필수)
     */
    @NotBlank(message = "작성자 이름은 필수입니다.")
    private String name;
    /**
     * 작성자 이메일 (필수, 이메일 형식이어야 함)
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

}
