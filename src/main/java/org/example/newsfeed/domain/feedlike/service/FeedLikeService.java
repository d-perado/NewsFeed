package org.example.newsfeed.domain.feedlike.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.FeedLike;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.feed.repository.FeedRepository;
import org.example.newsfeed.domain.feedlike.dto.FeedLikeDTO;
import org.example.newsfeed.domain.feedlike.dto.response.LikeFeedResponse;
import org.example.newsfeed.domain.feedlike.repository.FeedLikeRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedLikeService {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;

    /**
     * 피드 좋아요
     */
    public LikeFeedResponse likeFeed(Long feedId, String userEmail) {

//        사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있습니다.
//        본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.
//        같은 게시물에는 사용자당 한 번만 좋아요가 가능합니다.

        // 1. 해당 피드가 있는지 조회
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalStateException("해당 피드가 없습니다.")
        );

        // 2. 해당 유저가 있는지 조회
        User user = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new IllegalStateException("해당 이메일의 유저가 없습니다.")
        );

        // 3. 해당 피드의 유저와 userEmail이 같을 경우 -> 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다. 예외처리
        if (feed.getWriter().getEmail().equals(userEmail)) {
            throw new IllegalStateException("본인이 작성한 게시물에 좋아요를 남길 수 없습니다.");
        }

        // 4. 본인이 작성한 글이 아니라면 - 좋아요 여부 확인
        boolean isLiked = feedLikeRepository.existsByFeedAndUser(feed, user);

        // 4-1. 좋아요를 누르지 않았을 때
        if (!isLiked) {
            // 좋아요 생성
            FeedLike feedLike = new FeedLike(user, feed);

            // 좋아요 저장
            feedLikeRepository.save(feedLike);

            // 좋아요 카운트 증가
            feed.increaseLike();

            System.out.println("Asdf");
        } else {
            // 4-2. 이미 좋아요를 눌렀다면
            throw new IllegalStateException("같은 게시물에는 사용자당 한 번만 좋아요가 가능합니다.");
        }

        // 5. 현재 좋아요 수 반환
        Long currentLikeCount = feedLikeRepository.countByFeed(feed);

        // 6. FeedLikeDTO 반환
        FeedLike feedLike = feedLikeRepository.findByFeedAndUser(feed, user).get();
        FeedLikeDTO dto = FeedLikeDTO.from(feedLike);

        return LikeFeedResponse.from(dto, currentLikeCount);

    }
}
