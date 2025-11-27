package org.example.newsfeed.domain.feed.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feed.dto.request.CreateFeedRequest;
import org.example.newsfeed.domain.feed.dto.request.GetPeriodsFeedRequest;
import org.example.newsfeed.domain.feed.dto.request.UpdateFeedRequest;
import org.example.newsfeed.domain.feed.dto.response.*;
import org.example.newsfeed.domain.feed.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


//뉴스피드 조회 기능
//기본 정렬은 생성일자 기준으로 내림차순 정렬합니다.
//10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.


@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    /**
     * 피드 생성
     */
    @PostMapping("/feeds")
    public ResponseEntity<CreateFeedResponse> handlerCreateFeed(
            @Valid @RequestBody CreateFeedRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        String userEmail = user.getUsername();

        CreateFeedResponse result = feedService.createFeed(request, userEmail);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    /**
     * 피드 전체 조회 - 페이징 처리
     */
    @GetMapping("/feeds")
    public ResponseEntity<Page<GetFeedPageResponse>> handlerGetFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getFeeds(pageable));
    }

    /**
     * 피드 단건 조회
     */
    @GetMapping("/feeds/{feedId}")
    public ResponseEntity<GetFeedResponse> handlerGetOne(
            @PathVariable Long feedId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getOne(feedId));
    }


    /**
     * 피드 수정 - 추후, JWT 구현 후 리팩터링 예정
     */
    @PatchMapping("/feeds/{feedId}")
    public ResponseEntity<UpdateFeedResponse> handlerUpdateFeed(
            @PathVariable Long feedId,
            @Valid @RequestBody UpdateFeedRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        String userEmail = user.getUsername();

        return ResponseEntity.status(HttpStatus.OK).body(feedService.updateFeed(feedId, request, userEmail));
    }

    /**
     * 피드 삭제 - 추후, JWT 구현 후 리팩터링 예정
     */
    @DeleteMapping("/feeds/{feedId}")
    public ResponseEntity<Void> handlerDeleteFeed(
            @PathVariable Long feedId,
            @AuthenticationPrincipal UserDetails user
    ) {
        String userEmail = user.getUsername();

        feedService.delete(feedId, userEmail);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //기간별 검색 기능
    @GetMapping("/feeds/period")
    public ResponseEntity<Page<GetFeedResponse>> handlerGetPeriodFeeds(
            @RequestBody GetPeriodsFeedRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<GetFeedResponse> result = feedService.getPeriodFeeds(request.getStartDate(), request.getLastDate(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //내가 팔로우한 사람 피드 우선 조회
    @GetMapping("/users/me/feeds")
    public ResponseEntity<Page<GetFeedPageResponse>> handlerGetFollowPriorityFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GetFeedPageResponse> result = feedService.getFeedsByFollowPriority(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
