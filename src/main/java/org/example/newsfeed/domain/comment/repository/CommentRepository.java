package org.example.newsfeed.domain.comment.repository;

import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.common.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByFeed(Feed feed);
}
