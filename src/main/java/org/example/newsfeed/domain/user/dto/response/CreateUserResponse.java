package org.example.newsfeed.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateUserResponse {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String introduction;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
