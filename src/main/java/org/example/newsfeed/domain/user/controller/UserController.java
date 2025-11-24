package org.example.newsfeed.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.user.dto.CreateUserRequest;
import org.example.newsfeed.domain.user.dto.CreateUserResponse;
import org.example.newsfeed.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<CreateUserResponse> handlerCreateUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse createdUser = userService.createUser(createUserRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    // 단건조회
    @GetMapping("/{userid}")
    public void handler(@PathVariable Long userid) {

    }



}
