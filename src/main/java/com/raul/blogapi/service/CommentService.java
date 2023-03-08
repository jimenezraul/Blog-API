package com.raul.blogapi.service;

import com.raul.blogapi.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComments();
    CommentDto createComment(CommentDto comment);
    CommentDto getCommentById(Long id);
    List<CommentDto> getCommentByPostId(Long id);

    CommentDto updateComment(Long id, CommentDto comment);
    void deleteComment(Long id);
}

