package org.example.newsfeed.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.comment.dto.request.CreateCommentRequest;
import org.example.newsfeed.domain.comment.dto.response.CreateCommentResponse;
import org.example.newsfeed.domain.comment.dto.request.UpdateCommentRequest;
import org.example.newsfeed.domain.comment.dto.response.GetCommentPageResponse;
import org.example.newsfeed.domain.comment.dto.response.UpdateCommentResponse;
import org.example.newsfeed.domain.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/feeds/{feedId}/comments")
    public ResponseEntity<CreateCommentResponse> handlerCreateComment(
            @PathVariable("feedId") Long feedId,
            @Valid @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        String userEmail = user.getUsername();

        CreateCommentResponse result = commentService.save(feedId, request, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    // 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<Page<GetCommentPageResponse>> handlerGetComment(@PageableDefault(
            size = 10,
            page = 0,
            sort = "createdAt",
            direction = Sort.Direction.DESC
    ) Pageable pageable) {

        Page<GetCommentPageResponse> result = commentService.getAll(pageable);

        return ResponseEntity.ok(result);
    }


    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> handlerUpdateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        String userEmail = user.getUsername();

        UpdateCommentResponse result = commentService.update(commentId, request, userEmail);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> handlerDeleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails user
    ) {
        String userEmail = user.getUsername();

        commentService.delete(commentId, userEmail);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}