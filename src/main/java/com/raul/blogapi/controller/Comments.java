package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.CommentDTO;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.service.CommentService;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Comments {
    @Autowired
    private CommentService comments;
    @Autowired
    private PostService posts;

    @GetMapping("/comments")
    @JsonView(Views.Public.class)
    public List<CommentDTO> getAllComments() {
        return comments.getAllComments();
    }

    @PostMapping("/comments")
    public CommentDTO createComment(@Valid @RequestBody CommentDTO comment) {
        return comments.createComment(comment);
    }

    @GetMapping("/comments/{id}")
    @JsonView(Views.Public.class)
    public CommentDTO getCommentById(@PathVariable Long id) {
        return comments.getCommentById(id);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(comments.updateComment(id, comment));
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        comments.deleteComment(id);
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDTO> getCommentsByPost(@PathVariable Long id) {
        return comments.getCommentsByPost(id);
    }

    private CommentDTO toDto(Comment comment) {
        return new CommentDTO(comment);
    }
}
