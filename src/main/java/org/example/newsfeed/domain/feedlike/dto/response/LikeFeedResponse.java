package org.example.newsfeed.domain.feedlike.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.domain.feedlike.dto.FeedLikeDTO;

@Getter
@RequiredArgsConstructor
public class LikeFeedResponse {

    private final Long userId;
    private final String nickname;
    private final Long feedId;

    public static LikeFeedResponse from(FeedLikeDTO feedLikeDTO) {
        return new LikeFeedResponse(
                feedLikeDTO.getId(),
                feedLikeDTO.getUser().getNickname(),
                feedLikeDTO.getFeed().getId()
        );
    }
}
