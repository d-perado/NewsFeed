package org.example.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feed_likes")
@Getter
@NoArgsConstructor
public class FeedLike {

    // feed_likes 프라이머리 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 피드정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    public FeedLike(User user, Feed feed) {
        this.user = user;
        this.feed = feed;
    }
}
