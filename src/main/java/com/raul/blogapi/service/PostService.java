package com.raul.blogapi.service;

import com.raul.blogapi.dto.PostDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPost();
    PostDto createPost(PostDto post);
    PostDto getPostById(Long id);
    List<PostDto> getPostByUserId(Long id);

    PostDto updatePost(Long id, @Valid PostDto post);
    void deletePost(Long id);
}
