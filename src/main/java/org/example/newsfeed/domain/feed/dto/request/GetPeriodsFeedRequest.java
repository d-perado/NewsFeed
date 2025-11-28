package org.example.newsfeed.domain.feed.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetPeriodsFeedRequest {

    private LocalDateTime startDate;
    private LocalDateTime lastDate;
}
