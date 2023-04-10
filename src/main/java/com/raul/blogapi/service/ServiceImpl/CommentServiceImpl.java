package com.raul.blogapi.service.ServiceImpl;

import com.raul.blogapi.dto.CommentDTO;
import com.raul.blogapi.dto.CreateCommentDTO;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.CommentRepository;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO createComment(@Valid CreateCommentDTO commentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        User loggedUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        Comment comment = convertToEntity(commentDto);

        comment.setPost(new Post(commentDto.getPostId()));
        comment.setUser(loggedUser);
        comment.setCreatedAt();
        comment.setUpdatedAt();
        CommentDTO savedComment = new CommentDTO(commentRepository.save(comment));

        return savedComment;
    }

    @Override
    public List<CommentDTO> getCommentsByPost(Long id) {
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtDesc(id);

        return comments.stream().map(comment -> toDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO comment) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new UserNotFoundException("Comment not found"));

        existingComment.setText(comment.getText());
        existingComment.setUpdatedAt();

        Comment updatedComment = commentRepository.save(existingComment);

        CommentDTO updatedCommentDTO = new CommentDTO(updatedComment);
        return updatedCommentDTO;
    }




    private Comment convertToEntity(CreateCommentDTO comment) {
        Comment commentModel = new Comment();
        BeanUtils.copyProperties(comment, commentModel);
        return commentModel;
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

    @Override
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Comment not found"));
        return toDto(comment);
    }

    private CommentDTO toDto(Comment comment) {
        return new CommentDTO(comment);
    }
}
