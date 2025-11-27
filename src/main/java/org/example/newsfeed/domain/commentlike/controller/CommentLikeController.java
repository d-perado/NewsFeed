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

    // Like 좋아요 b
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<CommentLikeResponse> handlerLikeComment(
            @PathVariable("commentId") Long commentId,
            // Authentication 객체에서 사용자 이름(ID)을 안전하게 추출
            Authentication authentication
    ) {
        // 주로 User 엔티티의 username/email을 반환합니다.
        CommentLikeResponse likeResponse = commentLikeService.toggleLike(commentId, authentication.getName());

        return ResponseEntity.ok(likeResponse);
    }
}
