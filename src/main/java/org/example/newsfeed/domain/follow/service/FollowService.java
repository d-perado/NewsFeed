package org.example.newsfeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    FollowRepository followRepository;

    public CreateFollowResponse createFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if(existence) {
            throw new RuntimeException();
        }
        Follow follow = followRepository.findByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);

        return CreateFollowResponse.from(follow);
    }

    public void deleteFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if(existence) {
            throw new RuntimeException();
        }
        followRepository.deleteFollowByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
    }
}
