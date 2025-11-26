package org.example.newsfeed.domain.feedlike.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.FeedLike;
import org.example.newsfeed.common.entity.User;

@Getter
@RequiredArgsConstructor
public class FeedLikeDTO {

    private final Long id;
    private final User user;
    private final Feed feed;

    public static FeedLikeDTO from(FeedLike feedLike) {
        return new FeedLikeDTO(
                feedLike.getId(),
                feedLike.getUser(),
                feedLike.getFeed()
        );
    }
}
