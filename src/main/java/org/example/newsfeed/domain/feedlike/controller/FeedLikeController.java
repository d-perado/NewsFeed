package org.example.newsfeed.domain.feedlike.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feedlike.dto.response.LikeFeedResponse;
import org.example.newsfeed.domain.feedlike.service.FeedLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedLikeController {

    private final FeedLikeService feedLikeService;

    /**
     * 피드 좋아요
     */
    @PostMapping("/feeds/{feedId}/likes")
    public ResponseEntity<LikeFeedResponse> handlerCreateFeedLike(
            @PathVariable Long feedId,
            @AuthenticationPrincipal UserDetails user
    ) {

        String userEmail = user.getUsername();

        LikeFeedResponse result = feedLikeService.likeFeed(feedId, userEmail);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
