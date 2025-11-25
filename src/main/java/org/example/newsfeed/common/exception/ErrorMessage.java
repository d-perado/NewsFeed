package org.example.newsfeed.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_FEED(HttpStatus.NOT_FOUND, "해당 피드가 없습니다."),
    CANNOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST, "사용자는 자기 자신을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOWING(HttpStatus.BAD_REQUEST, "이미 해당 사용자를 팔로우하고 있습니다."),
    NOT_FOLLOWING(HttpStatus.BAD_REQUEST, "팔로우 관계가 존재하지 않습니다."),

    ;

    private final HttpStatusCode status;
    private final String message;
}
