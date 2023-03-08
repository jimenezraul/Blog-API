package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.CommentDto;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.service.CommentService;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CommentDto> getAllComments() {
        return comments.getAllComments();
    }

    @PostMapping("/comments")
    public Long createComment(@Valid @RequestBody CommentDto comment) {
        return comments.createComment(comment).getId();
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        comments.deleteComment(id);
    }

    private CommentDto toDto(Comment comment) {
        return new CommentDto(comment);
    }
}
