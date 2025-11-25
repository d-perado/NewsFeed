package org.example.newsfeed.domain.comment.service;

import org.example.newsfeed.common.entity.User;
import org.example.newsfeed.domain.comment.model.dto.CommentDTO;
import org.example.newsfeed.domain.comment.model.request.CreateCommentRequest;
import org.example.newsfeed.domain.comment.model.request.UpdateCommentRequest;
import org.example.newsfeed.domain.comment.model.response.CreateCommentResponse;
import org.example.newsfeed.domain.comment.model.response.GetCommentPageResponse;
import org.example.newsfeed.domain.comment.model.response.UpdateCommentResponse;
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

    // 생성
    public CreateCommentResponse save(Long feedId, Long userId, CreateCommentRequest request) {
        Feed feed = feedRepository.findById(feedId).orElseThrow(
                () -> new IllegalStateException("없는 피드입니다.")
        );

        User user = userRepository.findById(userId).orElseThrow(
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
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("없는 댓글입니다.")
        );
        comment.modify(request);
        CommentDTO dto = CommentDTO.from(comment);

        return UpdateCommentResponse.from(dto);

    }

    public void delete(Long commentId) {
        boolean existence = commentRepository.existsById(commentId);
        if (!existence) {
            throw new IllegalStateException(" 없는 댓글입니다. ");
        }
        commentRepository.deleteById(commentId);
    }
}

