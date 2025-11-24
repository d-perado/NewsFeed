package org.example.newsfeed.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.domain.comment.model.request.CreateCommentRequest;
import org.example.newsfeed.domain.comment.model.response.CreateCommentResponse;
import org.example.newsfeed.domain.comment.model.request.UpdateCommentRequest;
import org.example.newsfeed.domain.comment.model.response.GetCommentResponse;
import org.example.newsfeed.domain.comment.model.response.UpdateCommentResponse;
import org.example.newsfeed.domain.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 생성
    @PostMapping("/api/{feedId}/comments")
    public ResponseEntity<CreateCommentResponse> handlerCreateComment(
            @PathVariable("feedId") Long feedId,
            @PathVariable("userId") Long userId,
            @RequestBody CreateCommentRequest request
    ) {
       return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(feedId, userId,request));
    }


    // 조회
    @GetMapping("/api/comments")
    public ResponseEntity<GetCommentResponse> handlerGetAllComments(
            @PathVariable Long commentId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(commentId));
    }

    // 수정
    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> handlerUpdateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId,request));
    }

    // 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> handlerDeleteComment(
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
