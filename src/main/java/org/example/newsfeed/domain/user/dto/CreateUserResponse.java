package org.example.newsfeed.domain.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class CreateUserResponse {

    private Long id;
    private String nickname;
    private String email;
    private String password;
    private boolean isDeleted = false;
    private String introduction;

    public CreateUserResponse(Long id, String nickname, String email, String password, String introduction) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
    }

}
