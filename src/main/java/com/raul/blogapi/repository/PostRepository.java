package com.raul.blogapi.repository;

import com.raul.blogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();

    List<Post> findByUserId(Long id);
}
