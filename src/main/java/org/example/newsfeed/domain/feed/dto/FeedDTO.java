package org.example.newsfeed.domain.feed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.domain.user.dto.UserDTO;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class FeedDTO {

    private final Long id;
    private final UserDTO writer;
    private final String content;
    private Long likeCount = 0L;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public FeedDTO(Long id, UserDTO writer, String content, Long likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FeedDTO from(Feed feed) {
        return new FeedDTO(feed.getId(),
                UserDTO.from(feed.getWriter()),
                feed.getContent(),
                feed.getLikeCount(),
                feed.getCreatedAt(),
                feed.getUpdatedAt());
    }
}
