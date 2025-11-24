package org.example.newsfeed.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends TimeBaseEntity{
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

    public User(String nickname, String email, String password, String introduction) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
    }

    public void modify(String email, String password, String introduction) {
        this.email = email;
        this.password = password;
        this.introduction = introduction;
    }

}
