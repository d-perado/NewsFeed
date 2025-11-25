package org.example.newsfeed.domain.user.dto.request;


import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String nickname;
    private String email;
    private String password;
    private String introduction;

}
