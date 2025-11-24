package org.example.newsfeed.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    FollowService followService;

    @PostMapping("/api/follow")
    public ResponseEntity<CreateFollowResponse> handlerCreateFollow(@RequestParam Long followedUserId, @RequestParam Long followingUserId) {
        CreateFollowResponse createFollowResponse = followService.createFollow(followedUserId,followingUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createFollowResponse);
    }
}
