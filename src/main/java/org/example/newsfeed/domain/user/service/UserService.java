package org.example.newsfeed.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.auth.JwtToken;
import org.example.newsfeed.common.auth.JwtTokenProvider;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.example.newsfeed.domain.user.dto.request.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.request.DeleteUserRequest;
import org.example.newsfeed.domain.user.dto.request.UpdateUserRequest;
import org.example.newsfeed.domain.user.dto.response.CreateUserResponse;
import org.example.newsfeed.domain.user.dto.response.GetUserResponse;
import org.example.newsfeed.domain.user.dto.response.UpdateUserResponse;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    // UserService 속성
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 생성
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        User user = new User(request.getNickname(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getIntroduction());

        User savedUser = userRepository.save(user);

        return CreateUserResponse.from(savedUser);
    }


    // 사용자 단건조회
    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId) {

        // 유저 존재 여부
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        // 유저 탈퇴 여부
        if (findedUser.isDeleted()) {
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        return GetUserResponse.from(findedUser);
    }


    //사용자 자기자신 조회
    public GetUserResponse getUserSelf(UserDetails user) {

        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        return GetUserResponse.from(findUser);
    }


    // 사용자 전체조회
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAllUsers() {

        List<User> allUser = userRepository.getNonDeletedAllUser();

        return allUser.stream().map(GetUserResponse::from).toList();
    }


    // 사용자 수정
    @Transactional
    public UpdateUserResponse updateUser(UserDetails user, UpdateUserRequest request) {

        // 유저 존재 여부
        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        // 유저 탈퇴 여부
        if (findUser.isDeleted()) {
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        findUser.modify(
                passwordEncoder.encode(request.getPassword()),
                request.getIntroduction()
        );

        return UpdateUserResponse.from(findUser);
    }


    // 사용자 삭제
    @Transactional
    public void deleteUser(UserDetails user, DeleteUserRequest request) {

        User findUser = userRepository.findByEmail(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        if (!findUser.getEmail().equals(request.getEmail())) {
            throw new CustomException(ErrorMessage.EMAIL_NOT_MATCH);
        }

        if (!passwordEncoder.matches(request.getPassword(), findUser.getPassword())) {
            throw new CustomException(ErrorMessage.PASSWORD_NOT_MATCH);
        }
        followRepository.deleteAllByToOrFrom(findUser, findUser);

        userRepository.deleteById(findUser.getId());
    }


    //로그인
    public JwtToken login(String email, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }
}
