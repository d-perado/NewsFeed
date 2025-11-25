package org.example.newsfeed.domain.user.repository;

import org.example.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
  Optional<User> findByEmail(String email);

    List<User> getUserById(Long id);

    boolean findByPassword(String password);

}
