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

    public void increaseLike() {
        this.likeCount++;
    }

    public void decreaseLike() {
        if (likeCount > 0) {
            this.likeCount--;
        }
    }
}