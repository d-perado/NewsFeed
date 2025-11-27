package org.example.newsfeed.domain.feed.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feed.dto.FeedDTO;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetFeedPageResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GetFeedPageResponse from(FeedDTO dto) {
        return new GetFeedPageResponse(
                dto.getId(),
                dto.getWriter().getNickname(),
                dto.getContent(),
                dto.getLikeCount(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
