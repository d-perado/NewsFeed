package org.example.newsfeed.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.domain.feed.dto.FeedDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class GetFeedPageResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GetFeedPageResponse from(FeedDto dto) {
        return new GetFeedPageResponse(
                dto.getId(),
                dto.getWriter().getNickname(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }

}
