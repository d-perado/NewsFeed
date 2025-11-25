package org.example.newsfeed.domain.comment.dto.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String nickname;
    private String content;
}
