package org.example.newsfeed.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.user.dto.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.CreateUserResponse;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입 -> 유저생성
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        User user= new User(request.getNickname(), request.getEmail(), request.getPassword(), request.getIntroduction());
        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getNickname(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getIntroduction()
        );
    }
    // 유저조회(단건조회)
//    @Transactional(readOnly = true)
//    public







}
