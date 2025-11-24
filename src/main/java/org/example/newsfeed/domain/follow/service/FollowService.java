package org.example.newsfeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
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
            throw new CustomException(ErrorMessage.CANNOT_FOLLOW_SELF);
        }

        boolean existence = followRepository.existsByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);

        if (existence) {
            throw new CustomException(ErrorMessage.ALREADY_FOLLOWING);
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
            throw new CustomException(ErrorMessage.NOT_FOLLOWING);
        }
        followRepository.deleteFollowByFollowedUser_IdAndFollowingUser_Id(followedUserId, followingUserId);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowers(Long userId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Follow> followers = followRepository.findFollowsByFollowedUser_Id(userId, pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFollowedUser().getId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER))));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowings(Long userId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Follow> followers = followRepository.findFollowsByFollowingUser_Id(userId, pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFollowingUser().getId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER))));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }
}