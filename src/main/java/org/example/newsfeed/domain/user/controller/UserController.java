package org.example.newsfeed.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.auth.JwtToken;
import org.example.newsfeed.common.auth.LoginDto;
import org.example.newsfeed.domain.user.dto.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.CreateUserResponse;
import org.example.newsfeed.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<CreateUserResponse> handlerCreateUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse createdUser = userService.createUser(createUserRequest);
         return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @PostMapping("login")
    public ResponseEntity<JwtToken> handlerlogin(@RequestBody LoginDto loginDto){
        JwtToken jwtToken = userService.login(loginDto.getEmail(),loginDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> handlerLogout(@RequestHeader("Authorization") String token){
        userService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }
}
