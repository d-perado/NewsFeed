package org.example.newsfeed.domain.user.repository;

import org.example.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
