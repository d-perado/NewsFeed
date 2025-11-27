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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Column
    private String content;

    @Column
    private Long likeCount = 0L;

    public Feed(User writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public void modify(UpdateFeedRequest request) {
        this.content = request.getContent() != null ? request.getContent() : this.content;
    }

    // 좋아요 수 증가
    public void increaseLike() {
        this.likeCount++;
    }

    // 좋아요 수 감소
    public void decreaseLike() {
        if (likeCount > 0) {
            this.likeCount--;
        }
    }
}