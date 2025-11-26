package org.example.newsfeed.domain.feed.repository;

import org.example.newsfeed.common.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepositoryCustom {
    Page<Feed> findAllsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime lastDate, Pageable pageable);

    Page<Feed> findByFollowPriority(Long loginUserId, List<Long> followingIds, Pageable pageable);
}
