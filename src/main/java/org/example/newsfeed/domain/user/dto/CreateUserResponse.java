package org.example.newsfeed.domain.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class CreateUserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nickname;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private boolean isDeleted = false;
    @Column
    private String introduction;

    public CreateUserResponse(String nickname, String email, String password, String introduction) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
    }

}
