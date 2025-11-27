package org.example.newsfeed.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.follow.dto.response.CreateFollowResponse;
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

    //팔로우 생성
    @Transactional
    public CreateFollowResponse createFollow(UserDetails user, Long followingUserId) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_USER));

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
    
    //팔로우 삭제
    @Transactional
    public void deleteFollow(UserDetails user, Long followingUserId) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_USER));

        boolean existence = followRepository.existsByTo_IdAndFrom_Id(findUser.getId(), followingUserId);
        if (!existence) {
            throw new CustomException(ErrorMessage.NOT_FOLLOWING);
        }
        followRepository.deleteFollowByTo_IdAndFrom_Id(findUser.getId(), followingUserId);
    }
    
    //팔로잉 목록
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollowing(UserDetails user, int page, int size) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_USER));

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<User> following = followRepository.findFollowersByTo_Id(findUser.getId(), pageable);

        return following.map(UserDTO::from);
    }

    //팔로워 목록
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllFollower(UserDetails user, int page, int size) {
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(()-> new CustomException(ErrorMessage.NOT_FOUND_USER));

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<User> followers = followRepository.findFollowingsByFrom_Id(findUser.getId(), pageable);

        return followers.map(UserDTO::from);
    }

    //유저 존재 유무 체크
    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }
}