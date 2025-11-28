package org.example.newsfeed.domain.feed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.auth.SecurityUtil;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.comment.repository.CommentRepository;
import org.example.newsfeed.domain.feed.dto.FeedDTO;
import org.example.newsfeed.domain.feed.dto.request.CreateFeedRequest;
import org.example.newsfeed.domain.feed.dto.request.UpdateFeedRequest;
import org.example.newsfeed.domain.feed.dto.response.*;
import org.example.newsfeed.domain.feed.repository.FeedRepository;
import org.example.newsfeed.domain.feedlike.repository.FeedLikeRepository;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;
    private final FeedLikeRepository feedLikeRepository;

    // 피드 생성
    public CreateFeedResponse createFeed(CreateFeedRequest request, String email) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        Feed feed = new Feed(user, request.getContent());
        feedRepository.save(feed);

        FeedDTO dto = FeedDTO.from(feed);

        return CreateFeedResponse.from(dto);
    }

    //피드 전체 조회
    @Transactional(readOnly = true)
    public Page<GetFeedPageResponse> getFeeds(Pageable pageable) {
        Page<Feed> feedList = feedRepository.findAll(pageable);

        return feedList.map(feed -> GetFeedPageResponse.from(FeedDTO.from(feed)));
    }

    // 피드 단건 조회
    @Transactional(readOnly = true)
    public GetFeedResponse getOne(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        FeedDTO dto = FeedDTO.from(feed);

        return GetFeedResponse.from(dto);

    }


    // 피드 수정
    public UpdateFeedResponse updateFeed(Long feedId, UpdateFeedRequest request, String email) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        checkFeedOwnerEmail(email, feed);

        feed.modify(request);

        feedRepository.save(feed);

        FeedDTO dto = FeedDTO.from(feed);
        return UpdateFeedResponse.from(dto);
    }


    // 피드 삭제
    public void delete(Long feedId, String email) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        checkFeedOwnerEmail(email, feed);

        feedLikeRepository.deleteAllByFeed_Id(feedId);
        commentRepository.deleteAllByFeed_Id(feedId);

        feedRepository.delete(feed);
    }

    // 특정 기간별 검색한 피드를 조회
    @Transactional(readOnly = true)
    public Page<GetFeedResponse> getPeriodFeeds(LocalDateTime startDate, LocalDateTime lastDate, Pageable pageable) {
        Page<Feed> feeds = feedRepository.findAllsByCreatedAtBetween(startDate, lastDate, pageable);
        return feeds.map(FeedDTO::from).map(GetFeedResponse::from);
    }

    //내가 팔로우한 사람 피드를 우선 조회
    @Transactional(readOnly = true)
    public Page<GetFeedPageResponse> getFeedsByFollowPriority(Pageable pageable) {

        String email = SecurityUtil.getLoginUserEmail();

        User loginUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        List<Long> followingIds = followRepository
                .findAllByFrom_Id(loginUser.getId())
                .stream()
                .map(f -> f.getTo().getId())
                .toList();

        Page<Feed> feeds = feedRepository.findByFollowPriority(loginUser.getId(), followingIds, pageable);

        return feeds.map(feed -> GetFeedPageResponse.from(FeedDTO.from(feed)));
    }

    private static void checkFeedOwnerEmail(String email, Feed feed) {
        boolean emailEquals = feed.getWriter().getEmail().equals(email);

        if (!emailEquals) {
            throw new CustomException(ErrorMessage.EMAIL_NOT_MATCH);
        }
    }
}
