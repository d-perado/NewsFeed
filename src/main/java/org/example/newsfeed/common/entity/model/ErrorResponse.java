package org.example.newsfeed.common.entity.model;

import lombok.Getter;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.springframework.http.HttpStatusCode;

@Getter
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;

    // 커스텀 예외
    public ErrorResponse(ErrorMessage errorCode) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }

    // 자바 기본 예외
    public ErrorResponse(HttpStatusCode statusCode, String message) {
        this.status = statusCode.value();
        this.code = statusCode.toString();
        this.message = message;
    }
}
