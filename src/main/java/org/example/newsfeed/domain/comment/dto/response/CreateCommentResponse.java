package org.example.newsfeed.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.comment.dto.CommentDTO;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

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
