package com.raul.blogapi.service.UserServiceImpl;

import com.raul.blogapi.dto.CommentDto;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.CommentRepository;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;


    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = convertToEntity(commentDto);
        comment.setPost(new Post(commentDto.getPostId()));
        comment.setUser(new User(commentDto.getUserId()));
        Comment savedComment = commentRepository.save(comment);
        return toDto(savedComment);
    }


    private Comment convertToEntity(CommentDto comment) {
        Comment commentModel = new Comment();
        BeanUtils.copyProperties(comment, commentModel);
        return commentModel;
    }

    @Override
    public CommentDto getCommentById(Long id) {
        return null;
    }

    @Override
    public List<CommentDto> getCommentByPostId(Long id) {
        return null;
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto comment) {
        return null;
    }

    @Override
    public void deleteComment(Long id) {
        // delete comment by id and remove it from post
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Comment not found"));
        Post post = postRepository.findById(comment.getPost().getId()).orElseThrow(() -> new UserNotFoundException("Post not found"));
        post.getComments().remove(comment);
        postRepository.save(post);
        commentRepository.deleteById(id);
    }

    private CommentDto toDto(Comment comment) {
        return new CommentDto(comment);
    }
}
