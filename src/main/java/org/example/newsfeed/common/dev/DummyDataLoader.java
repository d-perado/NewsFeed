package org.example.newsfeed.common.dev;

import lombok.RequiredArgsConstructor;
import org.example.newsfeed.common.entity.*;
import org.example.newsfeed.domain.comment.repository.CommentRepository;
import org.example.newsfeed.domain.feed.repository.FeedRepository;
import org.example.newsfeed.domain.follow.repository.FollowRepository;
import org.example.newsfeed.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Transactional
@RequiredArgsConstructor
public class DummyDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        // 1. Users: 하루 1명씩, 20일치
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            User user = new User(
                    "user" + i,
                    "user" + i + "@example.com",
                    passwordEncoder.encode("Password#" + i),
                    "안녕하세요! 저는 user" + i + "입니다."
            );
            LocalDateTime date = now.minusDays(20 - i);
            setAuditFields(user, date);
            users.add(user);
        }
        users = userRepository.saveAll(users);

        // 2. Feeds: 하루 1개씩, 20일치
        List<Feed> feeds = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            User writer = users.get(random.nextInt(users.size()));
            Feed feed = new Feed(writer, "이것은 feed " + i + "의 내용입니다.");
            LocalDateTime date = now.minusDays(20 - i);
            setAuditFields(feed, date);
            feeds.add(feed);
        }
        feeds = feedRepository.saveAll(feeds);

        // 3. Comments: Feed 기준 하루 단위, 랜덤 시간
        for (int i = 1; i <= 20; i++) {
            Feed feed = feeds.get(i - 1); // Feed 순서대로
            User user = users.get(random.nextInt(users.size()));
            Comment comment = new Comment("댓글 " + i + "입니다.", feed, user);
            LocalDateTime date = feed.getCreatedAt().plusHours(random.nextInt(24));
            commentRepository.save(comment);
        }

        // 4. Follows: 랜덤 20개
        int created = 0;
        while (created < 20) {
            User following = users.get(random.nextInt(users.size()));
            User followed = users.get(random.nextInt(users.size()));
            if (!following.equals(followed)) {
                followRepository.save(Follow.from(followed, following));
                created++;
            }
        }
    }

    // ------------------- Reflection으로 createdAt/updatedAt 세팅 -------------------
    private void setAuditFields(TimeBaseEntity entity, LocalDateTime dateTime) {
        try {
            Field createdField = TimeBaseEntity.class.getDeclaredField("createdAt");
            createdField.setAccessible(true);
            createdField.set(entity, dateTime);

            Field updatedField = TimeBaseEntity.class.getDeclaredField("updatedAt");
            updatedField.setAccessible(true);
            updatedField.set(entity, dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
