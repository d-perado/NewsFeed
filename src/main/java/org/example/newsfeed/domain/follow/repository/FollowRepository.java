package org.example.newsfeed.domain.follow.repository;

import org.example.newsfeed.common.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.followedUser
    WHERE f.followedUser.id = :userId
""")

    Page<Follow> findFollowsByFollowedUser_Id(Long userId, Pageable pageable);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.followingUser
    WHERE f.followingUser.id = :userId
""")
    Page<Follow> findFollowsByFollowingUser_Id(Long userId, Pageable pageable);

    void deleteFollowByFollowedUser_IdAndFollowingUser_Id(Long followedUserId, Long followingUserId);
}
