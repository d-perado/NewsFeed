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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    @Transactional
    public CreateFollowResponse createFollow(UserDetails user, Long followingUserId) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new IllegalStateException("존재하지 않는 유저입니다."));

        if(findUser.getId().equals(followingUserId)) {
            throw new CustomException(ErrorMessage.CANNOT_FOLLOW_SELF);
        }

        boolean existence = followRepository.existsByTo_IdAndFrom_Id(findUser.getId(), followingUserId);

        if (existence) {
            throw new CustomException(ErrorMessage.ALREADY_FOLLOWING);
        }

        User followedUser = getUser(findUser.getId());
        User followingUser = getUser(followingUserId);

        Follow follow = Follow.from(followedUser, followingUser);

        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);
    }

    @Transactional
    public void deleteFollow(UserDetails user, Long followingUserId) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new IllegalStateException("존재하지 않는 유저입니다."));

        boolean existence = followRepository.existsByTo_IdAndFrom_Id(findUser.getId(), followingUserId);
        if (!existence) {
            throw new CustomException(ErrorMessage.NOT_FOLLOWING);
        }
        followRepository.deleteFollowByTo_IdAndFrom_Id(findUser.getId(), followingUserId);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowers(UserDetails user, int page, int size) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new IllegalStateException("존재하지 않는 유저입니다."));

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Follow> followers = followRepository.findFollowsByTo_Id(findUser.getId(), pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getTo().getId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER))));
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowings(UserDetails user, int page, int size) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new IllegalStateException("존재하지 않는 유저입니다."));

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Follow> followers = followRepository.findFollowsByFrom_Id(findUser.getId(), pageable);

        return followers.map(x-> UserDTO.from(userRepository.findById(x.getFrom().getId())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER))));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }
}