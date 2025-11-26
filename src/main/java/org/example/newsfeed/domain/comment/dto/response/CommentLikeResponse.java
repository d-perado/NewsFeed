package org.example.newsfeed.domain.comment.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.newsfeed.domain.comment.dto.CommentDTO;

@Getter
@AllArgsConstructor
public class CommentLikeResponse {

    private Long commentId;
    private Long userId;
    private boolean isLike;
    private Long LikeCount;
}
