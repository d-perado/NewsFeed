package org.example.newsfeed.domain.commentlike.service;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Comment;
import org.example.newsfeed.common.entity.CommentLike;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.commentlike.dto.response.CommentLikeResponse;
import org.example.newsfeed.domain.commentlike.repository.CommentLikeRepository;
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

        // 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER));
        // 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT));

        // 기존 좋아요 여부 확인
        Optional<CommentLike> existingLike = commentLikeRepository.findByUserAndComment(user, comment);

        boolean isLiked;

        if (existingLike.isPresent()) {
            // 좋아요 취소
            commentLikeRepository.delete(existingLike.get());
            comment.decreaseLikes();// 댓글 좋아요 수 감소
            isLiked = false;

        } else {
            // 좋아요 추가
            CommentLike newLike = new CommentLike(user, comment);
            commentLikeRepository.save(newLike);
            comment.increaseLikes();// 댓글 좋아요 수 증가
            isLiked = true;
        }

        // 최종 좋아요 수 조회
        Long likeCount = commentLikeRepository.countByComment(comment);

        // 응답 DTO 반환
        return new CommentLikeResponse(commentId, user.getId(), isLiked, likeCount);
    }
}
