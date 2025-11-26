package org.example.newsfeed.domain.follow.repository;

import org.example.newsfeed.common.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByTo_IdAndFrom_Id(Long followedUserId, Long followingUserId);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.to
    WHERE f.from.id = :userId
""")
    Page<Follow> findFollowsByTo_Id(Long userId, Pageable pageable);

    @Query("""
    SELECT f
    FROM Follow f
    JOIN FETCH f.to
    WHERE f.from.id = :userId
""")
    Page<Follow> findFollowsByFrom_Id(Long userId, Pageable pageable);

    void deleteFollowByTo_IdAndFrom_Id(Long followedUserId, Long followingUserId);

}
