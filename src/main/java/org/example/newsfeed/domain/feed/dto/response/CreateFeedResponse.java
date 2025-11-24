package org.example.newsfeed.domain.feed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.domain.feed.dto.FeedDto;
import org.example.newsfeed.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedResponse {

    private Long id;
    private UserDto writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CreateFeedResponse from(FeedDto dto) {
        return new CreateFeedResponse(
                dto.getId(),
                dto.getWriter(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
