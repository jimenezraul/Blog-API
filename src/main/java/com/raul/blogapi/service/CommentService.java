package com.raul.blogapi.service;

import com.raul.blogapi.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllComments();
    CommentDTO createComment(CommentDTO comment);
    CommentDTO getCommentById(Long id);
    List<CommentDTO> getCommentByPostId(Long id);

    CommentDTO updateComment(Long id, CommentDTO comment);
    void deleteComment(Long id);
}

