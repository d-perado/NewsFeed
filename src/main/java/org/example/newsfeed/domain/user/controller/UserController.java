package org.example.newsfeed.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.auth.JwtToken;
import org.example.newsfeed.common.auth.LoginDto;
import org.example.newsfeed.domain.user.dto.request.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.request.DeleteUserRequest;
import org.example.newsfeed.domain.user.dto.request.UpdateUserRequest;
import org.example.newsfeed.domain.user.dto.response.CreateUserResponse;
import org.example.newsfeed.domain.user.dto.response.GetUserResponse;
import org.example.newsfeed.domain.user.dto.response.UpdateUserResponse;
import org.example.newsfeed.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 생성
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> handlerCreateUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // 사용자 단건조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> handlerGetUser(@PathVariable Long userId) {
        GetUserResponse oneUser = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(oneUser);
    }
    
    //자기 자신 조회
    @GetMapping("/users/self")
    public ResponseEntity<GetUserResponse> handlerGetUserSelf(@AuthenticationPrincipal UserDetails user) {
        GetUserResponse result = userService.getUserSelf(user);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 사용자 전체조회
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> handlerGetAllUsers() {
        List<GetUserResponse> allUser = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }

    // 사용자 수정
    @PatchMapping("/users")
    public ResponseEntity<UpdateUserResponse> handlerUpdateUser(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody UpdateUserRequest request) {

        UpdateUserResponse updatedUser = userService.updateUser(user, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    // 사용자 삭제 소프트딜리트
    @PatchMapping("/users/deletion")
    public ResponseEntity<Void> handlerDeleteUser(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody DeleteUserRequest request) {

        userService.deleteUser(user, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 로그인 기능
    @PostMapping("/users/login")
    public ResponseEntity<JwtToken> handlerLogin(@RequestBody LoginDto loginDto) {
        JwtToken jwtToken = userService.login(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
}
