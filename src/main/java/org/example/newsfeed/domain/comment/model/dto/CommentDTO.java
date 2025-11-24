package org.example.newsfeed.domain.comment.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.domain.feed.model.dto.FeedDTO;
import org.example.newsfeed.domain.user.model.dto.UserDTO;

import java.time.LocalDateTime;

//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class FeedDto {
//
//    private Long id;
//    private UserDto writer;
//    private String content;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    public static org.example.newsfeed.domain.feed.model.dto.FeedDto from(Feed feed) {
//        return new org.example.newsfeed.domain.feed.model.dto.FeedDto(
//                feed.getId(),
//                UserDto.from(
//                        feed.getWriter()
//                ),
//                feed.getContent(),
//                feed.getCreatedAt(),
//                feed.getUpdatedAt());
//    }
//}

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private FeedDTO feed;
    private UserDTO user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDTO from(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                FeedDTO.from(
                        comment.getFeed()),
                UserDTO.from(comment.getUser()),
                comment.getContent(),
                comment.getFeed().getCreatedAt(),
                comment.getFeed().getUpdatedAt()
        );
    }

}
