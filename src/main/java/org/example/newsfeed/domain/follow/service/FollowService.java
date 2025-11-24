package org.example.newsfeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.follow.dto.CreateFollowResponse;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.example.newsfeed.domain.user.dto.UserDTO;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    @Transactional
    public CreateFollowResponse createFollow(Long followedUserId, Long followingUserId) {
        if(followedUserId.equals(followingUserId)) {
            throw new RuntimeException("자기자신팔로우 불가능");
        }

        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);

        if (existence) {
            throw new RuntimeException("이미 팔로우한 사이입니다.");
        }

        User followedUser = getUser(followedUserId);
        User followingUser = getUser(followingUserId);

        Follow follow = Follow.from(followedUser, followingUser);

        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);
    }

    @Transactional
    public void deleteFollow(Long followedUserId, Long followingUserId) {
        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
        if (!existence) {
            throw new RuntimeException("없는 팔로우 관계입니다.");
        }
        followRepository.deleteFollowByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowers(Long userId, int pageSize, int pageNo) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<Follow> followers = followRepository.findFollowsByFollowedUser_Id(userId, pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFollowedUser().getId())
                .orElseThrow(()->new RuntimeException("존재하지않는 유저"))));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowings(Long userId, int pageSize, int pageNo) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);
        Page<Follow> followers = followRepository.findFollowsByFollowingUser_Id(userId, pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFollowingUser().getId())
                .orElseThrow(()->new RuntimeException("존재하지않는 유저"))));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
    }
}