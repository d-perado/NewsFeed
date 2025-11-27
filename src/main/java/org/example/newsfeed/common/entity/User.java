package org.example.newsfeed.common.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE users SET is_Deleted = true WHERE id=?")

public class User extends TimeBaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private boolean isDeleted;

    @Column
    private String introduction;

    public User(String nickname, String email, String password, String introduction) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.introduction = introduction;
        this.isDeleted = false;
    }

    public void modify(String email, String password, String introduction) {
        this.email = email;
        this.password = password;
        this.introduction = introduction;
    }

    //권한을 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    //사용자를 구분하는 값
    @Override
    public String getUsername() {
        return this.email;   // 로그인 기준: email
    }

    // 계정이 만료되었는지 여부 (true = 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 기능을 사용하지 않으므로 항상 true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 기능을 사용하지 않기 때문에 항상 true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
