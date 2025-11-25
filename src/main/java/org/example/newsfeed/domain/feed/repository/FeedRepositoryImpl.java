package org.example.newsfeed.domain.feed.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.Feed;
import org.example.newsfeed.common.entity.QFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom{

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
}
