package org.example.newsfeed.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.domain.feed.dto.FeedDto;
import org.example.newsfeed.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateFeedResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CreateFeedResponse from(FeedDto dto) {
        return new CreateFeedResponse(
                dto.getId(),
                dto.getWriter().getNickname(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
