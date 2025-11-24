package org.example.newsfeed.domain.user.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @Column(unique = true)
    private String nickname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String introduction;

    public CreateUserRequest(String nickname, String email, String password, String introduction) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
    }




}
