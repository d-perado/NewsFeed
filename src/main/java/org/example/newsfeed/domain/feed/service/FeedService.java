package org.example.newsfeed.domain.feed.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.feed.dto.FeedDTO;
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
    public CreateFeedResponse createFeed(CreateFeedRequest request, String email) {

        // 해당 email의 유저가 있는지 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("해당 이메일의 유저가 없습니다.")
        );

        Feed feed = new Feed(user, request.getContent());
        feedRepository.save(feed);
        FeedDTO dto = FeedDTO.from(feed);

        return CreateFeedResponse.from(dto);
    }

    /**
     * 피드 전체 조회 - 페이징 처리
     */
    @Transactional(readOnly = true)
    public Page<GetFeedPageResponse> getFeeds(Pageable pageable) {
        Page<Feed> feedList = feedRepository.findAll(pageable);
        return feedList.map(feed -> GetFeedPageResponse.from(FeedDTO.from(feed)));
    }


    /**
     * 피드 단건 조회
     */
    @Transactional(readOnly = true)
    public GetFeedResponse getOne(Long feedId) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        FeedDTO dto = FeedDTO.from(feed);

        return GetFeedResponse.from(dto);

    }


    /**
     * 피드 수정 - 추후, JWT 구현 후 본인만 수정 가능 및 본인이 아닐 때 수정할 시에 예외처리 기능 추가 구현 예정
     */
    public UpdateFeedResponse updateFeed(Long feedId, UpdateFeedRequest request, String email) {
        // 해당 id의 피드가 있는지 확인
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        // 로그인 유저가 해당 피드를 작성한 것이 맞는지 이메일로 확인
        boolean emailEquals = feed.getWriter().getEmail().equals(email);

        // 작성자가 다르다면 예외 처리
        if (!emailEquals) {
            throw new IllegalStateException("이메일이 다릅니다.");
        }

        // 작성자가 같다면 해당 피드 수정
        feed.modify(request);

        // 수정한 피드 저장소에 저장
        feedRepository.save(feed);

        FeedDTO dto = FeedDTO.from(feed);
        return UpdateFeedResponse.from(dto);
    }


    /**
     * 피드 삭제 - 추후, JWT 구현 후 본인만 삭제 가능 및 본인이 아닐 때 삭제할 시에 예외처리 기능 추가 구현 예정
     */
    public void delete(Long feedId, String email) {
        // 해당 id의 피드가 있는지 확인
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        // 로그인 유저가 해당 피드를 작성한 것이 맞는지 이메일로 확인
        boolean emailEquals = feed.getWriter().getEmail().equals(email);

        // 작성자가 다르다면 예외 처리
        if (!emailEquals) {
            throw new IllegalStateException("이메일이 다릅니다.");
        }

        // 작성자가 같다면 해당 피드 삭제
        feedRepository.delete(feed);
    }
}
