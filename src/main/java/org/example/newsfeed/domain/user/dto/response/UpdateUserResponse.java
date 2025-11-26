package org.example.newsfeed.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.User;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateUserResponse {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String introduction;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UpdateUserResponse from(User user) {
        return new UpdateUserResponse(user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getIntroduction(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
