package org.example.newsfeed.domain.comment.model.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String nickname;
    private String content;
}
