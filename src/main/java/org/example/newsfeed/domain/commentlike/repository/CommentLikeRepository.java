package org.example.newsfeed.domain.commentlike.repository;

import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.common.entity.CommentLike;
import org.example.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserAndComment(User user , Comment comment);

    // 좋아요 수 카운트 메서드
    Long countByComment(Comment comment);
}
