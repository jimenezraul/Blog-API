package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.CommentDTO;
import com.raul.blogapi.dto.CreateCommentDTO;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.service.CommentService;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(comments.getAllComments());
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CreateCommentDTO comment) {
        CommentDTO newComment = comments.createComment(comment);
        URI location = URI.create(String.format("/comments/%s", newComment.getId()));
        return ResponseEntity.created(location).body(newComment);
    }

    @GetMapping("/comments/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(comments.getCommentById(id));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(comments.updateComment(id, comment));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        comments.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long id) {
        return ResponseEntity.ok(comments.getCommentsByPost(id));
    }
}
