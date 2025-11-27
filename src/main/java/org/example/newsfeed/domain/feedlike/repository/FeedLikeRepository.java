package org.example.newsfeed.domain.feedlike.repository;

import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.FeedLike;
import org.example.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    // 피드에 대한 사용자의 좋아요 존재 여부
    boolean existsByFeedAndUser(Feed feed, User user);

    // 피드와 사용자 기준 좋아요 조회
    Optional<FeedLike> findByFeedAndUser(Feed feed, User user);

    void deleteAllByFeed_Id(Long feedId);
    // 해당 피드의 좋아요 개수
    Long countByFeed(Feed feed);

}
