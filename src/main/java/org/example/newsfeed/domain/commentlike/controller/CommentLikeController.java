package org.example.newsfeed.domain.commentlike.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.commentlike.dto.response.CommentLikeResponse;
import org.example.newsfeed.domain.commentlike.service.CommentLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글 좋아요
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<CommentLikeResponse> handlerLikeComment(
            @PathVariable("commentId") Long commentId,
            Authentication authentication
    ) {
        // 좋아요 처리 로직 실행
        CommentLikeResponse likeResponse = commentLikeService.toggleLike(commentId, authentication.getName());

        return ResponseEntity.ok(likeResponse);
    }
}
