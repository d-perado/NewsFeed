package org.example.newsfeed.domain.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.comment.dto.CommentDTO;
import java.time.LocalDateTime;


@Getter
@RequiredArgsConstructor
public class GetCommentPageResponse {

    private final Long id;
    private final String nickname;
    private final String content;
    private final Long likeCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GetCommentPageResponse from(CommentDTO dto) {
        return new GetCommentPageResponse(
                dto.getId(),
                dto.getUser().getNickname(),
                dto.getContent(),
                dto.getLikeCount(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}