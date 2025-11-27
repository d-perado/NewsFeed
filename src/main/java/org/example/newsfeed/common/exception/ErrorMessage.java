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
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    NOT_FOUND_FOLLOWING(HttpStatus.NOT_FOUND, "팔로우 관계가 존재하지 않습니다."),
    NOT_FOUND_LIKE(HttpStatus.NOT_FOUND, "좋아요를 누르지 않은 게시물입니다."),

    TOKEN_AUTHORITY_MISSING(HttpStatus.UNAUTHORIZED, "토큰에 권한 정보가 없습니다."),

    CANNOT_FOLLOW_SELF(HttpStatus.BAD_REQUEST, "사용자는 자기 자신을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOWING(HttpStatus.BAD_REQUEST, "이미 해당 사용자를 팔로우하고 있습니다."),

    EMAIL_NOT_MATCH(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    DUPLICATE_LIKE(HttpStatus.CONFLICT, "이미 해당 게시물에 좋아요를 눌렀습니다."),

    SELF_LIKE_FORBIDDEN(HttpStatus.FORBIDDEN, "본인이 작성한 게시물에는 좋아요를 누를 수 없습니다."),

    ;

    private final HttpStatusCode status;
    private final String message;

}
