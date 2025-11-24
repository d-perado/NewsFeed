package org.example.newsfeed.domain.feed.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.domain.user.model.dto.UserDTO;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedDTO {

    private Long id;
    private UserDTO writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FeedDTO from(Feed feed) {
        return new FeedDTO(
                feed.getId(),
                UserDTO.from(
                        feed.getWriter()
                ),
                feed.getContent(),
                feed.getCreatedAt(),
                feed.getUpdatedAt());
    }
}
