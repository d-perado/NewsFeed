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
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public static CommentDTO from(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                FeedDTO.from(comment.getFeed()),
                UserDTO.from(comment.getUser()),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
