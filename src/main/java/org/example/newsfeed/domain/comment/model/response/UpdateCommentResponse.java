package org.example.newsfeed.domain.comment.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.domain.comment.model.dto.CommentDTO;

@Getter
@AllArgsConstructor
public class UpdateCommentResponse {

    private Long id;
    private String nickname;
    private String content;

    public static UpdateCommentResponse form(CommentDTO dto) {
        return new UpdateCommentResponse(
                dto.getId(),
                dto.getUser().getNickname(),
                dto.getContent()
        );
    }


}
