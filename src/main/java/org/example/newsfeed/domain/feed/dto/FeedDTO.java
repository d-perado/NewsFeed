package org.example.newsfeed.domain.feed.dto;


//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDto {
//
//    private Long id;
//    private String username;
//    private String email;
//    private String password;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
//
//    public static UserDto from(User user) {
//        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getCreatedAt(),
//                user.getModifiedAt());
//    }
//
//}


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.domain.user.dto.UserDTO;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedDTO {

    private Long id;
    private UserDTO writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FeedDTO from(Feed feed) {
        return new FeedDTO(feed.getId(),
                UserDTO.from(feed.getWriter()),
                feed.getContent(),
                feed.getCreatedAt(),
                feed.getUpdatedAt());
    }

}
