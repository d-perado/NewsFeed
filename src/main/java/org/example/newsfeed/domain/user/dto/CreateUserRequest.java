package org.example.newsfeed.domain.user.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String nickname;
    private String email;
    private String password;
    private String introduction;

}
