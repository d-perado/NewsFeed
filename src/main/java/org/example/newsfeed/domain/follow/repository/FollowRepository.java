package org.example.newsfeed.domain.follow.repository;

import org.example.newsfeed.common.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);

    boolean existsByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);

}
