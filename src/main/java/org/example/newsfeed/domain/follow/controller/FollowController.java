package org.example.newsfeed.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.service.FollowService;
import org.example.newsfeed.domain.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follows")
    public ResponseEntity<CreateFollowResponse> handlerCreateFollow(@AuthenticationPrincipal UserDetails user,
                                                                    @RequestParam Long fromUserId) {
        CreateFollowResponse createFollowResponse = followService.createFollow(user,fromUserId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createFollowResponse);
    }

    @DeleteMapping("/follows")
    public ResponseEntity<Void> handlerDeleteFollow(@AuthenticationPrincipal UserDetails user,
                                                    @RequestParam Long fromUserId) {
        followService.deleteFollow(user, fromUserId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/followers")
    public ResponseEntity<Page<UserDTO>> handlerGetAllFollowers(@AuthenticationPrincipal UserDetails user,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {

        Page<UserDTO> result = followService.getAllFollowing(user, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/followings")
    public ResponseEntity<Page<UserDTO>> handlerGetAllFollowings(@AuthenticationPrincipal UserDetails user,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {

        Page<UserDTO> result = followService.getAllFollower(user, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
