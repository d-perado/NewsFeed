package org.example.newsfeed.domain.comentlike.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentLikeResponse {

    private final Long commentId;
    private final Long userId;
    private final boolean isLike;
    private final Long LikeCount;
}
