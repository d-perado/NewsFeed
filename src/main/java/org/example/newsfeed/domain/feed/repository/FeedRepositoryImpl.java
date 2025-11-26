package org.example.newsfeed.domain.feed.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.QFeed;
import org.example.newsfeed.common.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Feed> findAllsByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        QFeed feed = QFeed.feed;

        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null) {
            builder.and(feed.createdAt.goe(startDate));
        }

        if (endDate != null) {
            builder.and(feed.createdAt.loe(endDate));
        }

        List<Feed> results = queryFactory
                .selectFrom(feed)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(feed.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(feed.count())
                .from(feed)
                .where(builder)
                .fetchOne();

        total = total != null ? total : 0L;

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Feed> findByFollowPriority(Long loginUserId, List<Long> followingIds, Pageable pageable) {

        QFeed feed = QFeed.feed;
        QUser user = QUser.user;

        NumberExpression<Integer> followPriority =
                Expressions.numberTemplate(
                        Integer.class,
                        "CASE WHEN {0} IN ({1}) THEN 0 ELSE 1 END",
                        user.id,
                        followingIds.isEmpty() ? Collections.singleton(-1L) : followingIds
                );

        List<Feed> result = queryFactory
                .selectFrom(feed)
                .join(feed.writer, user).fetchJoin()
                .orderBy(
                        followPriority.asc(),  // 팔로우한 사람 먼저
                        feed.createdAt.desc()  // 최신순
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(feed.count())
                .from(feed)
                .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
