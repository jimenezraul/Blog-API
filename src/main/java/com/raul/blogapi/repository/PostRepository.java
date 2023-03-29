package com.raul.blogapi.repository;

import com.raul.blogapi.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();

    List<Post> findByUserId(Long id);

    List<Post> findAllByUserId(Long id, PageRequest createdAt);

   long countByUserId(Long id);
}
