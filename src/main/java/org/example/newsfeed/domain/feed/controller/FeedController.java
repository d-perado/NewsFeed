package org.example.newsfeed.domain.feed.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feed.dto.request.CreateFeedRequest;
import org.example.newsfeed.domain.feed.dto.response.CreateFeedResponse;
import org.example.newsfeed.domain.feed.dto.response.GetFeedPageResponse;
import org.example.newsfeed.domain.feed.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//뉴스피드 조회 기능
//기본 정렬은 생성일자 기준으로 내림차순 정렬합니다.
//10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.



@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    // 게시물 생성
    @PostMapping("users/{userId}/feeds")
    public ResponseEntity<CreateFeedResponse> handlerCreateFeed(
            @PathVariable Long userId,
            @Valid @RequestBody CreateFeedRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.createFeed(userId, request));
    }

    // 게시물 전체 조회
    // 게시물 수정, 삭제는 작성자 본인만 처리할 수 있습니다.
    // 기본 정렬은 생성일자 기준으로 내림차순 정렬합니다.
    // 10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.
    @GetMapping("/feeds")
    public ResponseEntity<Page<GetFeedPageResponse>> handlerGetFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getFeeds(pageable));
    }
}
