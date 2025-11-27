package org.example.newsfeed.domain.comentlike.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.common.entity.CommentLike;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.comentlike.dto.response.CommentLikeResponse;
import org.example.newsfeed.domain.comentlike.repository.CommentLikeRepository;
import org.example.newsfeed.domain.comment.repository.CommentRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeResponse toggleLike(Long commentId, String email) {

        // 1. User와 Comment 엔티티 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT));

        // 2. 좋아요 기록 확인
        Optional<CommentLike> existingLike = commentLikeRepository.findByUserAndComment(user, comment);

        boolean isLiked;

        if (existingLike.isPresent()) {
            // 3. 좋아요 취소
            commentLikeRepository.delete(existingLike.get());

            // 💡 Comment 엔티티의 좋아요 카운트 감소
            comment.decreaseLikes();
            isLiked = false;

        } else {
            // 4. 좋아요 추가
            CommentLike newLike = new CommentLike(user, comment);
            commentLikeRepository.save(newLike);

            // Comment 엔티티의 좋아요 카운트 증가
            comment.increaseLikes();
            isLiked = true;
        }

        // 5. 최종 좋아요 수 계산
        Long likeCount = commentLikeRepository.countByComment(comment);

        // 6. 응답 DTO 반환
        return new CommentLikeResponse(commentId, user.getId(), isLiked, likeCount);
    }
}
