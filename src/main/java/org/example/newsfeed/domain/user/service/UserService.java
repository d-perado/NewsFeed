package org.example.newsfeed.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.auth.JwtToken;
import org.example.newsfeed.common.auth.JwtTokenProvider;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // 사용자 생성
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        User user= new User(request.getNickname(), request.getEmail(),passwordEncoder.encode(request.getPassword()), request.getIntroduction());
        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getNickname(),
                savedUser.getEmail(),
                savedUser.getIntroduction(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }
    // 사용자 단건조회
    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId) {
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
        if (findedUser.isDeleted()) {
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        return new GetUserResponse(
                findedUser.getId(),
                findedUser.getNickname(),
                findedUser.getEmail(),
                findedUser.getIntroduction(),
                findedUser.getCreatedAt(),
                findedUser.getUpdatedAt()
        );
    }
    // 사용자 전체조회
    @Transactional(readOnly = true)
    public List<GetUserResponse> getAllUsers() {
        List<User> allUser = userRepository.getNonDeletedAllUser();
        return allUser.stream().map((x) -> new GetUserResponse(
                x.getId(),
                x.getNickname(),
                x.getEmail(),
                x.getIntroduction(),
                x.getCreatedAt(),
                x.getUpdatedAt()
        )).toList();

    }
    // 사용자 수정
    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );
        if (user.isDeleted()) {
            throw new CustomException(ErrorMessage.NOT_FOUND_USER);
        }

        user.modify(
                request.getEmail(),
                request.getPassword(),
                request.getIntroduction()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getIntroduction(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    // 사용자 삭제
    @Transactional
    public void deleteUser(Long userId, DeleteUserRequest request) {
        // 1-1. 사용자 아이디가 존재하지 않을때 예외처리
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        // 1-2. 사용자 아이디와 비밀번호가 일치하지 않는 경우
        if (!user.getEmail().equals(request.getEmail())) {
            throw new CustomException(ErrorMessage.EMAIL_NOT_MATCH);
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new CustomException(ErrorMessage.PASSWORD_NOT_MATCH);
        }
        // 1-3. 사용자 아이디가 존재할때 삭제처리
        userRepository.deleteById(userId);
    }

    public JwtToken login(String email, String password) {
        // 1.email + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);

    }
}
