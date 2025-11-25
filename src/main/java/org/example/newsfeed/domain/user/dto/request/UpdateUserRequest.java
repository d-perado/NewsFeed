package org.example.newsfeed.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String email;
    private String password;
    private String introduction;

}
