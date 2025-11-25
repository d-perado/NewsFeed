package org.example.newsfeed.domain.user.dto.request;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String nickname;
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*#?&])([a-zA-Z0-9@$!%*#?&]{8,16})$",
            message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함하여 8~16자로 입력해야 합니다.")
    private String password;
    private String introduction;

}
