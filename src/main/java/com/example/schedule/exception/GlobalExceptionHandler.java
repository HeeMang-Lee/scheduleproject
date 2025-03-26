package com.example.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
/**
 * 전역 예외 처리 클래스.
 * 컨트롤러 전역에서 발생하는 예외를 처리하여 통일된 에러 응답을 제공한다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ResponseStatusException 처리 (예: 비밀번호 불일치, ID 없음 등)
     * @param e ResponseStatusException 예외 객체
     * @return ErrorResponse와 함께 HTTP 상태 반환
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(e.getStatusCode().value(),e.getReason()));
    }

    /**
     * 유효성 검사 실패 예외 처리 (@Valid 검증 실패)
     * @param e MethodArgumentNotValidException 예외 객체
     * @return ErrorResponse와 함께 400 Bad Request 반환
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(400,message));
    }

    /**
     * 처리되지 않은 일반 예외 처리
     * @param e 모든 Exception 타입
     * @return ErrorResponse와 함께 500 Internal Server Error 반환
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500,"서버 내부 오류가 발생했습니다."));
    }
}
