package org.example.newsfeed.domain.feed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.feed.dto.FeedDto;
import org.example.newsfeed.domain.feed.dto.request.CreateFeedRequest;
import org.example.newsfeed.domain.feed.dto.response.CreateFeedResponse;
import org.example.newsfeed.domain.feed.dto.response.GetFeedPageResponse;
import org.example.newsfeed.domain.feed.dto.response.GetFeedResponse;
import org.example.newsfeed.domain.feed.repository.FeedRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {


    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    /**
     * 피드 생성
     * @param userId
     * @param request
     * @return
     */
    public CreateFeedResponse createFeed(Long userId, CreateFeedRequest request) {
        // 해당 id의 유저가 있는지 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        Feed feed = new Feed(user, request.getContent());
        feedRepository.save(feed);
        FeedDto dto = FeedDto.from(feed);

        return CreateFeedResponse.from(dto);
    }

    /**
     * 피드 전체 조회 - 페이징 처리
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<GetFeedPageResponse> getFeeds(Pageable pageable) {
        Page<Feed> feedList = feedRepository.findAll(pageable);
        return feedList.map(i -> GetFeedPageResponse.from(FeedDto.from(i)));
    }


    /**
     * 피드 단건 조회
     * @param feedId
     * @return
     */
    @Transactional(readOnly = true)
    public GetFeedResponse getOne(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalStateException("해당 피드가 없습니다.")
        );

        FeedDto dto = FeedDto.from(feed);

        return GetFeedResponse.from(dto);

    }
}
