package org.example.newsfeed.common.auth;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    // email로 사용자 조회
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
    }

    // 조회된 USER 엔티티를 스프링 시큐리티의 UserDetails 객체로 변환
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()) // 인증 시 사용할 username(email)
                .password(user.getPassword()) // 인코딩된 비밀번호
                .roles("USER")  // 기본 USER 권한 부여
                .build();
    }
}
