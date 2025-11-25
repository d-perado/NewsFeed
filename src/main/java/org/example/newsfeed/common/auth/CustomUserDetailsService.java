package org.example.newsfeed.common.auth;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // CustomUserDetail 반환 loadUserByUsername()
    // CustomUserDetail implemnets UserDetails 클래스 생성

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new IllegalStateException("해당하는 회원을 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private CustomUserDetails createUserDetails(User user) {

        return new CustomUserDetails(user.getId(), user.getEmail(), passwordEncoder.encode(user.getPassword()),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }


}
