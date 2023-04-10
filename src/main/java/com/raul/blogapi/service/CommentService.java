package com.raul.blogapi.service;

import com.raul.blogapi.dto.CommentDTO;
import com.raul.blogapi.dto.CreateCommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getAllComments();
    CommentDTO createComment(CreateCommentDTO comment);
    List<CommentDTO> getCommentsByPost(Long id);

    CommentDTO updateComment(Long commentId, CommentDTO comment);

    void deleteComment(Long id);

    CommentDTO getCommentById(Long id);
}

