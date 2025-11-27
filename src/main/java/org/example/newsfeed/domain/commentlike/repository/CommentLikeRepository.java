package org.example.newsfeed.domain.commentlike.repository;

import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.common.entity.CommentLike;
import org.example.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 특정 유저가 특정 댓글에 누른 좋아요 여부 조회
    Optional<CommentLike> findByUserAndComment(User user , Comment comment);

    // 해당 댓글의 총 좋아요 개수 조회
    Long countByComment(Comment comment);
}
