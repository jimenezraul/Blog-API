package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.PostDto;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Posts {
    @Autowired
    private PostService service;
    @GetMapping("/posts")
    @JsonView(Views.Public.class)
    public List<PostDto> getAllPost() {
        return service.getAllPost();
    }

    @GetMapping("/posts/{id}")
    @JsonView(Views.Public.class)
    public PostDto getPostById(@PathVariable Long id) {
        return service.getPostById(id);
    }

    @GetMapping("/posts/user/{id}")
    @JsonView(Views.Private.class)
    public List<PostDto> getPostByUserId(@PathVariable Long id) {
        return service.getPostByUserId(id);
    }

    @PostMapping("/posts")
    public Long createPost(@Valid @RequestBody PostDto post) {
        return service.createPost(post).getId();
    }

    @PutMapping("/posts/{id}")
    public PostDto updatePost(@PathVariable Long id, @Valid @RequestBody PostDto post) {
        return service.updatePost(id, post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        service.deletePost(id);
    }
}