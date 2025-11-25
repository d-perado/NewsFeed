package org.example.newsfeed.domain.user.dto.request;

import lombok.Getter;

@Getter
public class DeleteUserRequest {

    private String email;
    private String password;

}
