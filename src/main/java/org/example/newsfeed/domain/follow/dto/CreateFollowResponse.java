package org.example.newsfeed.domain.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Follow;

@Getter
@RequiredArgsConstructor
public class CreateFollowResponse {
    private final Long followedId;
    private final Long followingId;

    public static CreateFollowResponse from(Follow follow) {
        return new CreateFollowResponse(
                follow.getTo().getId(),
                follow.getFrom().getId()
        );
    }
}
