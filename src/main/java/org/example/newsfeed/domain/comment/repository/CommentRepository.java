package org.example.newsfeed.domain.comment.repository;

import org.example.newsfeed.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByFeed_Id(Long feedId);

    Page<Comment> findAllByFeed_Id(Long feedId, Pageable pageable);
}
