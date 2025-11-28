package org.example.newsfeed.domain.comment.service;

import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.common.exception.CustomException;
import org.example.newsfeed.common.exception.ErrorMessage;
import org.example.newsfeed.domain.comment.dto.CommentDTO;
import org.example.newsfeed.domain.comment.dto.request.CreateCommentRequest;
import org.example.newsfeed.domain.comment.dto.request.UpdateCommentRequest;
import org.example.newsfeed.domain.comment.dto.response.CreateCommentResponse;
import org.example.newsfeed.domain.comment.dto.response.GetCommentPageResponse;
import org.example.newsfeed.domain.comment.dto.response.UpdateCommentResponse;
import org.example.newsfeed.domain.commentlike.repository.CommentLikeRepository;
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


@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 댓글 생성
    public CreateCommentResponse save(Long feedId, CreateCommentRequest request, String email) {

        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_FEED)
        );

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_USER)
        );

        Comment comment = new Comment(request.getContent(), feed, user);

        commentRepository.save(comment);
        CommentDTO dto = CommentDTO.from(comment);

        return CreateCommentResponse.from(dto);

    }


    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public Page<GetCommentPageResponse> getCommentByFeed(Pageable pageable, Long feedId) {

        Page<Comment> commentPage = commentRepository.findAllByFeed_Id(feedId, pageable);

        return commentPage.map(comment ->
                GetCommentPageResponse.from(CommentDTO.from(comment))
        );
    }

    // 댓글 수정
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request, String email) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT)
        );

        checkCommentOwnerEmail(email, comment);

        comment.modify(request);
        CommentDTO dto = CommentDTO.from(comment);

        return UpdateCommentResponse.from(dto);

    }


    // 댓글 삭제
    public void delete(Long commentId, String email) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorMessage.NOT_FOUND_COMMENT)
        );

        checkCommentOwnerEmail(email, comment);

        commentLikeRepository.deleteAllByComment_Id(commentId);

        commentRepository.deleteById(commentId);
    }


    //댓글 주인의 Email 과 일치 확인
    private static void checkCommentOwnerEmail(String email, Comment comment) {
        boolean emailEquals = comment.getUser().getEmail().equals(email);

        if (!emailEquals) {
            throw new CustomException(ErrorMessage.EMAIL_NOT_MATCH);
        }
    }
}