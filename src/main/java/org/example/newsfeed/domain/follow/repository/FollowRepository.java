package org.example.newsfeed.domain.follow.repository;

import org.example.newsfeed.common.entity.Follow;
import org.example.newsfeed.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로우 관계 존재 유무
    boolean existsByTo_IdAndFrom_Id(Long followedUserId, Long followingUserId);

    // 팔로워 목록
    @Query("""
    SELECT f.from
    FROM Follow f
    WHERE f.to.id = :userId
""")
    Page<User> findFollowersByTo_Id(Long userId, Pageable pageable);

    // 팔로잉 목록
    @Query("""
    SELECT f.to
    FROM Follow f
    WHERE f.from.id = :userId
""")
    Page<User> findFollowingsByFrom_Id(Long userId, Pageable pageable);

    // 팔로우 관계 삭제
    void deleteFollowByTo_IdAndFrom_Id(Long followedUserId, Long followingUserId);

    List<Follow> findAllByTo_Id(Long followingUserId);
}
