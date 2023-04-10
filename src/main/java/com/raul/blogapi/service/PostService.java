package com.raul.blogapi.service;

import com.raul.blogapi.dto.CreatePostDTO;
import com.raul.blogapi.dto.PostDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {
    List<PostDTO> getLatestPosts(int page, int size);
    PostDTO createPost(@Valid CreatePostDTO post);
    PostDTO getPostById(Long id);
    List<PostDTO> getPostByUserId(Long id, int page, int size);

    PostDTO updatePost(Long id, @Valid PostDTO post);
    void deletePost(Long id);
    Long getPostByUserIdCount(Long id);

    Long getPostCount();
}
