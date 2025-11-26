package org.example.newsfeed.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feed.dto.FeedDTO;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetFeedResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GetFeedResponse from(FeedDTO dto, Long likeCount) {
        return new GetFeedResponse(
                dto.getId(),
                dto.getWriter().getNickname(),
                dto.getContent(),
                likeCount,
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
