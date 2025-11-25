package org.example.newsfeed.domain.feed.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.feed.dto.FeedDto;
import org.example.newsfeed.domain.feed.dto.request.CreateFeedRequest;
import org.example.newsfeed.domain.feed.dto.request.UpdateFeedRequest;
import org.example.newsfeed.domain.feed.dto.response.CreateFeedResponse;
import org.example.newsfeed.domain.feed.dto.response.GetFeedPageResponse;
import org.example.newsfeed.domain.feed.dto.response.GetFeedResponse;
import org.example.newsfeed.domain.feed.dto.response.UpdateFeedResponse;
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
     */
    public CreateFeedResponse createFeed(Long userId, CreateFeedRequest request) {
        // 해당 id의 유저가 있는지 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        Feed feed = new Feed(user, request.getContent());
        feedRepository.save(feed);
        FeedDto dto = FeedDto.from(feed);

        return CreateFeedResponse.from(dto);
    }

    /**
     * 피드 전체 조회 - 페이징 처리
     */
    @Transactional(readOnly = true)
    public Page<GetFeedPageResponse> getFeeds(Pageable pageable) {
        Page<Feed> feedList = feedRepository.findAll(pageable);
        return feedList.map(i -> GetFeedPageResponse.from(FeedDto.from(i)));
    }


    /**
     * 피드 단건 조회
     */
    @Transactional(readOnly = true)
    public GetFeedResponse getOne(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        FeedDto dto = FeedDto.from(feed);

        return GetFeedResponse.from(dto);

    }


    /**
     * 피드 수정 - 추후, JWT 구현 후 본인만 수정 가능 및 본인이 아닐 때 수정할 시에 예외처리 기능 추가 구현 예정
     */
    public UpdateFeedResponse updateFeed(Long feedId, UpdateFeedRequest request) {
        // 해당 id의 피드가 있는지 확인
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        // 피드 수정
        feed.modify(request);

        // 수정한 피드 저장소에 저장
        feedRepository.save(feed);
        FeedDto dto = FeedDto.from(feed);
        return UpdateFeedResponse.from(dto);
    }


    /**
     * 피드 삭제 - 추후, JWT 구현 후 본인만 삭제 가능 및 본인이 아닐 때 삭제할 시에 예외처리 기능 추가 구현 예정
     */
    public void delete(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        feedRepository.delete(feed);
    }
}
