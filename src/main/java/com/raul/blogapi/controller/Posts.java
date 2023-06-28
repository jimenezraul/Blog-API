package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.CreatePostDTO;
import com.raul.blogapi.dto.PostDTO;
import com.raul.blogapi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class Posts {
    @Autowired
    private PostService service;
    @GetMapping("/posts")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<PostDTO>> getAllPost(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "50") int size) {

        List<PostDTO> posts = service.getLatestPosts(page, size);
        return  ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPostById(id));
    }

    @GetMapping("/posts/user/{id}")
    @JsonView(Views.Private.class)
    public ResponseEntity<List<PostDTO>> getPostByUserId(@PathVariable Long id,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "50") int size) {
        return ResponseEntity.ok(service.getPostByUserId(id, page, size));
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> createPost(@Valid @RequestBody CreatePostDTO post) {
        PostDTO newPost = service.createPost(post);
        URI location = URI.create(String.format("/posts/%s", newPost.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return ResponseEntity.created(location).headers(headers).body(newPost.getId());
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO post) {
        return ResponseEntity.ok(service.updatePost(id, post));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        service.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/user/{id}/count")
    public ResponseEntity<Long> getPostByUserIdCount(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPostByUserIdCount(id));
    }

    @GetMapping("/posts/count")
    public ResponseEntity<Long> getPostCount() {
        return ResponseEntity.ok(service.getPostCount());
    }
}