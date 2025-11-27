package org.example.newsfeed.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequest {

    @NotBlank(message = "이메일을 작성해주세요")
    private String email;

    @NotBlank(message = "패스워드를 작성해주세요")
    private String password;

}
