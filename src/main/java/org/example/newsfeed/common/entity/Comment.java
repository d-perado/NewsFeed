package org.example.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.domain.comment.dto.request.UpdateCommentRequest;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column(nullable = false)
    private Long likeCount = 0L;

    @CreatedDate
    @Column(updatable = false) // 생성 시간은 수정되지 않도록 설정
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String content, Feed feed, User user) {
        this.content = content;
        this.feed = feed;
        this.user = user;
    }

    public void modify(UpdateCommentRequest request) {
        this.content = request.getContent() != null ? request.getContent() : this.content;
    }

    public void increaseLikes() {
        this.likeCount++;
    }

    public void decreaseLikes() {
        if(this.likeCount > 0) {
            this.likeCount--;
        }
    }

}
