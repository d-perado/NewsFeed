package org.example.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.domain.comment.dto.request.UpdateCommentRequest;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private Long likeCount = 0L;

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

    // 요청(request)에 새로운 content가 있으면 그걸로 변경하고, 없으면(=null이면) 기존 content를 그대로 사용
    public void modify(UpdateCommentRequest request) {
        this.content = request.getContent() != null ? request.getContent() : this.content;
    }

    // 좋아요 갯수 추가
    public void increaseLikes() {
        this.likeCount++;
    }

    // 좋아요 갯수 감소
    public void decreaseLikes() {
        if(this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
