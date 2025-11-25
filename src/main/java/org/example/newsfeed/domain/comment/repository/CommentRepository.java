package org.example.newsfeed.domain.comment.repository;

import org.example.newsfeed.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
