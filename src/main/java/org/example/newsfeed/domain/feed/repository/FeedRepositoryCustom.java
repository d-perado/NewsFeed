package org.example.newsfeed.domain.feed.repository;

import org.example.newsfeed.common.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FeedRepositoryCustom {
    Page<Feed> findAllsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime lastDate, Pageable pageable);
}
