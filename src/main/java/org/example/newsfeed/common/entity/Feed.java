package org.example.newsfeed.common.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.domain.feed.dto.request.UpdateFeedRequest;

@Entity
@Table(name = "feeds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends TimeBaseEntity {

    // feeds 프라이머리 키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 유저키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;
    // 댓글
    @Column
    private String content;
    // feed 생성자
    public Feed(User writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    // 피드 업데이트 기능
    public void modify(UpdateFeedRequest request) {
        this.content = request.getContent() != null ? request.getContent() : this.content;
    }
}