package org.example.newsfeed.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.domain.feed.dto.FeedDTO;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateFeedResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UpdateFeedResponse from(FeedDTO dto) {
        return new UpdateFeedResponse(
                dto.getId(),
                dto.getWriter().getNickname(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
