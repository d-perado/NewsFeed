package org.example.newsfeed.domain.comment.service;

import org.example.newsfeed.common.entity.CommentLike;
import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.comment.dto.CommentDTO;
import org.example.newsfeed.domain.comment.dto.request.CreateCommentRequest;
import org.example.newsfeed.domain.comment.dto.request.UpdateCommentRequest;
import org.example.newsfeed.domain.comment.dto.response.CommentLikeResponse;
import org.example.newsfeed.domain.comment.dto.response.CreateCommentResponse;
import org.example.newsfeed.domain.comment.dto.response.GetCommentPageResponse;
import org.example.newsfeed.domain.comment.dto.response.UpdateCommentResponse;
import org.example.newsfeed.domain.comment.repository.CommentLikeRepository;
import org.example.newsfeed.domain.feed.repository.FeedRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.example.newsfeed.common.entity.Comment;

import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;


    // 생성
    public CreateCommentResponse save(Long feedId, CreateCommentRequest request, String email) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalStateException("없는 피드입니다.")
        );

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        Comment comment = new Comment(
                request.getContent(),
                feed,
                user
        );
        commentRepository.save(comment);
        CommentDTO dto = CommentDTO.from(comment);

        return CreateCommentResponse.from(dto);

    }


    // 전체 조회
    @Transactional(readOnly = true)
    public Page<GetCommentPageResponse> getAll(Pageable pageable) {

        Page<Comment> commentPage = commentRepository.findAll(pageable);

        return commentPage.map(comment -> GetCommentPageResponse.from(CommentDTO.from(comment)));
    }

    // 댓글 수정
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request, String email) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("없는 댓글입니다.")
        );

        boolean emailEquals = comment.getUser().getEmail().equals(email);

        if(!emailEquals) {
            throw new IllegalStateException("이메일이 다릅니다.");
        }

        comment.modify(request);
        CommentDTO dto = CommentDTO.from(comment);

        return UpdateCommentResponse.from(dto);

    }

    // 댓글 삭제
    public void delete(Long commentId, String email) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("없는 댓글입니다.")
        );

        boolean emailEquals = comment.getUser().getEmail().equals(email);

        if(!emailEquals) {
            throw new IllegalStateException("이메일이 다릅니다.");
        }

        commentRepository.deleteById(commentId);
    }


    public CommentLikeResponse toggleLike(Long commentId, String email) {

        // 1. User와 Comment 엔티티 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("댓글을 찾을 수 없습니다."));

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
            comment.increaseLikeCount();
            isLiked = true;
        }

        // 5. 최종 좋아요 수 계산
        Long likeCount = commentLikeRepository.countByComment(comment);

        // 6. 응답 DTO 반환
        return new CommentLikeResponse(commentId, user.getId(), isLiked, likeCount);
    }
}

