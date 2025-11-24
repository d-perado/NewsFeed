package org.example.newsfeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.dto.FollowDTO;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.example.newsfeed.domain.user.dto.UserDTO;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateFollowResponse createFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if (existence) {
            throw new RuntimeException();
        }
        User followedUser = userRepository.findById(followedUserId).orElseThrow(
                () -> new RuntimeException("없는유저")
        );
        User followingUser = userRepository.findById(followingUserId).orElseThrow(
                () -> new RuntimeException("없는유저")
        );

        Follow follow = Follow.from(followedUser, followingUser);

        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);
    }

    @Transactional
    public void deleteFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if (existence) {
            throw new RuntimeException();
        }
        followRepository.deleteFollowByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findFollowers(Long userId, int pageSize, int pageNo) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<Follow> followers = followRepository.findFollowsByFollowedUser_Id(userId, pageable);


        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFollowedUser().getId())
                .orElseThrow(()->new RuntimeException("존재하지않는 유저"))));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findFollowing(Long userId, int pageSize, int pageNo) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<Follow> followers = followRepository.findFollowsByFollowingUser_Id(userId, pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFollowingUser().getId())
                .orElseThrow(()->new RuntimeException("존재하지않는 유저"))));
    }
}