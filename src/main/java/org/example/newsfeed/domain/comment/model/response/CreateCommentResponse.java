package org.example.newsfeed.domain.comment.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.domain.comment.model.dto.CommentDTO;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateCommentResponse {

    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CreateCommentResponse from(CommentDTO dto) {
        return new CreateCommentResponse(
                dto.getId(),
                dto.getUser().getNickname(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
