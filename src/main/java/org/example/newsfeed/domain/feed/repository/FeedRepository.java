package org.example.newsfeed.domain.feed.repository;

import org.example.newsfeed.common.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
}
