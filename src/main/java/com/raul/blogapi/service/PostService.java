package com.raul.blogapi.service;

import com.raul.blogapi.dto.PostDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();
    PostDTO createPost(PostDTO post);
    PostDTO getPostById(Long id);
    List<PostDTO> getPostByUserId(Long id);

    PostDTO updatePost(Long id, @Valid PostDTO post);
    void deletePost(Long id);
}
