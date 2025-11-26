package org.example.newsfeed.domain.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feed.dto.FeedDTO;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateFeedResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CreateFeedResponse from(FeedDTO dto) {
        return new CreateFeedResponse(
                dto.getId(),
                dto.getWriter().getNickname(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
