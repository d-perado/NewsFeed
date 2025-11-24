package org.example.newsfeed.domain.feed.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feed.dto.request.CreateFeedRequest;
import org.example.newsfeed.domain.feed.dto.response.CreateFeedResponse;
import org.example.newsfeed.domain.feed.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 게시물 작성
    @PostMapping("/{userId}")
    public ResponseEntity<CreateFeedResponse> handlerCreateFeed(
            @PathVariable Long userId,
            @Valid @RequestBody CreateFeedRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.createFeed(userId, request));
    }
}
