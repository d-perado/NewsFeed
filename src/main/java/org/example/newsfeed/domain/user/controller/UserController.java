package org.example.newsfeed.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.auth.JwtToken;
import org.example.newsfeed.common.auth.LoginDto;
import org.example.newsfeed.domain.user.dto.request.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.request.DeleteUserRequest;
import org.example.newsfeed.domain.user.dto.request.UpdateUserRequest;
import org.example.newsfeed.domain.user.dto.response.CreateUserResponse;
import org.example.newsfeed.domain.user.dto.response.GetAllUserResponse;
import org.example.newsfeed.domain.user.dto.response.GetOneUserResponse;
import org.example.newsfeed.domain.user.dto.response.UpdateUserResponse;
import org.example.newsfeed.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 생성
    @PostMapping
    public ResponseEntity<CreateUserResponse> handlerCreateUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    // 사용자 단건조회
    @GetMapping("/{userId}")
    public ResponseEntity<GetOneUserResponse> handlerGetUser(@PathVariable Long userId) {
        GetOneUserResponse oneUser = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(oneUser);
    }
    // 사용자 전체조회
    @GetMapping
    public ResponseEntity<GetAllUserResponse> handlerGetAllUsers() {
        GetAllUserResponse allUser = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }
    // 사용자 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<UpdateUserResponse> handlerUpdateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request){
        UpdateUserResponse updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
    // 사용자 삭제 소프트딜리트
    @PatchMapping("/{userId}/deletion")
    public ResponseEntity<Void> handlerDeleteUser(@PathVariable Long userId, @RequestBody DeleteUserRequest request) {
        userService.deleteUser(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("login")
    public ResponseEntity<JwtToken> handlerLogin(@RequestBody LoginDto loginDto){
        JwtToken jwtToken = userService.login(loginDto.getEmail(),loginDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
}
