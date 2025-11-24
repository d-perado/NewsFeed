package org.example.newsfeed.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.service.FollowService;
import org.example.newsfeed.domain.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows")
    public ResponseEntity<CreateFollowResponse> handlerCreateFollow(@RequestParam Long followedUserId, @RequestParam Long followingUserId) {
        CreateFollowResponse createFollowResponse = followService.createFollow(followedUserId,followingUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createFollowResponse);
    }

    @DeleteMapping("/follows")
    public ResponseEntity<Void> handlerDeleteFollow(@RequestParam Long followedUserId, @RequestParam Long followingUserId) {
        followService.deleteFollow(followedUserId, followingUserId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/follower/{userId}")
    public ResponseEntity<Page<UserDTO>> handlerGetAllFollowers(@PathVariable Long userId,
                                                                @RequestParam int pageSize,
                                                                @RequestParam int pageNo) {

        Page<UserDTO> result = followService.getAllFollowers(userId, pageSize, pageNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<Page<UserDTO>> handlerGetAllFollowings(@PathVariable Long userId,
                                                                @RequestParam int pageSize,
                                                                @RequestParam int pageNo) {

        Page<UserDTO> result = followService.getAllFollowings(userId, pageSize, pageNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
