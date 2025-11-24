package org.example.newsfeed.domain.feed.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateFeedRequest {

    @NotBlank(message = "내용을 입력해주세요")
    private String content;
}
