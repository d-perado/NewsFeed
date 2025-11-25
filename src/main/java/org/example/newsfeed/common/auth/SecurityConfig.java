package org.example.newsfeed.common.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //웹 브라우저 기본 로그인 창 사용 안함
                .httpBasic(AbstractHttpConfigurer::disable)
                //CSRF 공경방지 기능 꺼두기 (REST API + JWT는 세션이 없기 때문에 필요 없음)
                .csrf(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않겠다는 설정 (JWT 방식이기 때문에 필요 없음)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // URL 주소별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                                // 로그인 API는 누구나 접근 가능
                                .requestMatchers("/users", "/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users/*").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}