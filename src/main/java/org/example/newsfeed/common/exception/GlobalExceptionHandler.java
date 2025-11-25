package org.example.newsfeed.common.exception;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.common.entity.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 자바 기본 예외 등록
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException 발생 {} : ", e.getMessage());

        String message = Objects.requireNonNull(e.getBindingResult().getFieldError().getDefaultMessage());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(e.getStatusCode(), message));
    }


    // 커스텀 예외 처리 등록
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("CustomException 발생 {} : ", e.getErrorMessage());

        return ResponseEntity
                .status(e.getErrorMessage().getStatus())
                .body(new ErrorResponse(e.getErrorMessage()));
    }
}
