package com.raul.blogapi.repository;

import com.raul.blogapi.dto.CommentDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long id);

    Comment findByPostId(Long id);

    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long id);

}
