package org.example.newsfeed.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.auth.JwtToken;
import org.example.newsfeed.common.auth.JwtTokenProvider;
import org.example.newsfeed.domain.user.dto.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.CreateUserResponse;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입 -> 유저생성
    public CreateUserResponse createUser(CreateUserRequest request) {

        User user= new User(request.getNickname(), request.getEmail(), request.getPassword(), request.getIntroduction());
        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getNickname(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getIntroduction(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }
    // 유저조회(단건조회)
//    @Transactional(readOnly = true)
//    public



    public JwtToken login(String email, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);


    }



}
