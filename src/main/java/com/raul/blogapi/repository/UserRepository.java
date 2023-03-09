package com.raul.blogapi.repository;

import com.raul.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    boolean existsByUsername(String username);

    Optional<Object> findByUsername(String username);
}

