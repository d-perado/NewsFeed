package org.example.newsfeed.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.domain.feed.dto.FeedDTO;
import org.example.newsfeed.domain.user.dto.UserDTO;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentDTO {

    private final Long id;
    private final FeedDTO feed;
    private final UserDTO user;
    private final String content;
    private Long likeCount = 0L;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentDTO(Long id, FeedDTO feed, UserDTO user, String content, Long likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.feed = feed;
        this.user = user;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentDTO from(Comment comment) {
        return new CommentDTO(comment.getId(),
                FeedDTO.from(comment.getFeed()),
                UserDTO.from(comment.getUser()),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
