package org.example.newsfeed.domain.feedlike.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.FeedLike;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.feed.repository.FeedRepository;
import org.example.newsfeed.domain.feedlike.dto.FeedLikeDTO;
import org.example.newsfeed.domain.feedlike.dto.response.LikeFeedResponse;
import org.example.newsfeed.domain.feedlike.repository.FeedLikeRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedLikeService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;

    // 피드 좋아요
    public LikeFeedResponse likeFeed(Long feedId, String userEmail) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        if (feed.getWriter().getEmail().equals(userEmail)) {
            throw new CustomException(ErrorMessage.SELF_LIKE_FORBIDDEN);
        }

        boolean isLiked = feedLikeRepository.existsByFeedAndUser(feed, user);

        if (!isLiked) {
            FeedLike feedLike = new FeedLike(user, feed);

            feedLikeRepository.save(feedLike);

            feed.increaseLike();
        } else {
            throw new CustomException(ErrorMessage.DUPLICATE_LIKE);
        }

        Long currentLikeCount = feedLikeRepository.countByFeed(feed);

        FeedLike feedLike = feedLikeRepository.findByFeedAndUser(feed, user).get();
        FeedLikeDTO dto = FeedLikeDTO.from(feedLike);

        return LikeFeedResponse.from(dto, currentLikeCount);
    }


    // 피드 좋아요 취소
    public void unlikeFeed(Long feedId, String userEmail) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        FeedLike feedLike = feedLikeRepository.findByFeedAndUser(feed, user).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_LIKE)
        );

        feedLikeRepository.delete(feedLike);

        feed.decreaseLike();
    }
}