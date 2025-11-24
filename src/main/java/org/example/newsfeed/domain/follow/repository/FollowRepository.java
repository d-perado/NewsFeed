package org.example.newsfeed.domain.follow.repository;

import org.example.newsfeed.common.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);

    boolean existsByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);

    void deleteFollowByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);
}
