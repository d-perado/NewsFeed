package org.example.newsfeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateFollowResponse createFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if(existence) {
            throw new RuntimeException();
        }
        User followedUser = userRepository.findById(followedUserId).orElseThrow(
                ()-> new RuntimeException("없는유저")
        );
        User followingUser = userRepository.findById(followingUserId).orElseThrow(
                ()-> new RuntimeException("없는유저")
        );

        Follow follow = Follow.from(followedUser, followingUser);

        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);
    }

    @Transactional
    public void deleteFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if(existence) {
            throw new RuntimeException();
        }
        followRepository.deleteFollowByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
    }
}
