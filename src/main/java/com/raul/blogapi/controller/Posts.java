package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.PostDTO;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Posts {
    @Autowired
    private PostService service;
    @GetMapping("/posts")
    @JsonView(Views.Public.class)
    public List<PostDTO> getAllPost(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "50") int size) {

        List<PostDTO> posts = service.getLatestPosts(page, size);
        return  posts;
    }

    @GetMapping("/posts/{id}")
    @JsonView(Views.Public.class)
    public PostDTO getPostById(@PathVariable Long id) {
        return service.getPostById(id);
    }

    @GetMapping("/posts/user/{id}")
    @JsonView(Views.Private.class)
    public List<PostDTO> getPostByUserId(@PathVariable Long id, @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "50") int size) {
        return service.getPostByUserId(id, page, size);
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> createPost(@Valid @RequestBody PostDTO post) {
        service.createPost(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/posts/{id}")
    public PostDTO updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO post) {
        return service.updatePost(id, post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        service.deletePost(id);
    }

    @GetMapping("/posts/user/{id}/count")
    public Long getPostByUserIdCount(@PathVariable Long id) {
        return service.getPostByUserIdCount(id);
    }
}