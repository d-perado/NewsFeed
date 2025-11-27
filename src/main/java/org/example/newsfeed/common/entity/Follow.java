package org.example.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor
public class Follow {

    // follow 프라이머리 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 팔로잉 유저키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id")
    private User to;

    // 팔로워 유저키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User from;

    private Follow(User to, User from) {
        this.to = to;
        this.from = from;
    }

    public static Follow from(User followedUser, User followingUser) {
        return new Follow(followedUser, followingUser);
    }
}

