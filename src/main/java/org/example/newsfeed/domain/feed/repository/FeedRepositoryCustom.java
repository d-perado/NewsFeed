package org.example.newsfeed.domain.feed.repository;

import org.example.newsfeed.common.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepositoryCustom {

    // 특정 기간별 검색한 피드를 조회
    Page<Feed> findAllsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime lastDate, Pageable pageable);

    // 내가 팔로우한 사람 피드를 우선 조회
    Page<Feed> findByFollowPriority(Long loginUserId, List<Long> followingIds, Pageable pageable);

}
