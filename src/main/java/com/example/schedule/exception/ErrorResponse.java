package com.example.schedule.exception;

import lombok.Getter;
/**
 * 예외 발생 시 클라이언트에 반환되는 공통 응답 객체.
 * 상태 코드와 메시지를 포함한다.
 */
@Getter
public class ErrorResponse {
    private final int status;
    private final String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
