package org.example.newsfeed.domain.user.dto;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateUserResponse {
    private final Long id;
    private final String nickname;
    private final String email;
    private final String password;
    private final String introduction;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;

    public CreateUserResponse(Long id, String nickname, String email, String password, String introduction, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

}
