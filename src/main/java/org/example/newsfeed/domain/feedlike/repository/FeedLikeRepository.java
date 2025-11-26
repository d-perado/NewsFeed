package org.example.newsfeed.domain.feedlike.repository;

import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.FeedLike;
import org.example.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    boolean existsByFeedAndUser(Feed feed, User user);

    Optional<FeedLike> findByFeedAndUser(Feed feed, User user);
}
