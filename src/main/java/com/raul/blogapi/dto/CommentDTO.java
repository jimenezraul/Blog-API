package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.Comment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonView(Views.Public.class)
public class CommentDTO {

    private Long id;
    @Size(min = 2, message = "Description should have at least 2 characters")
    @NonNull
    private String text;
    @NonNull
    private Long postId;
    @NonNull
    private Long userId;
    private String user;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public CommentDTO(Comment comment) {
        BeanUtils.copyProperties(comment, this);
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.user = comment.getUser().getName();
        this.created_at = comment.getCreatedAt();
        this.updated_at = comment.getUpdated_at();
    }
}
