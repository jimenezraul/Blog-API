package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.Comment;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @JsonView(Views.Public.class)
    private Long id;
    @Size(min = 2, message = "Description should have at least 2 characters")
    @NonNull
    @JsonView(Views.Public.class)
    private String text;
    @NonNull
    @JsonView(Views.Public.class)
    private Long postId;
    @NonNull
    @JsonView(Views.Public.class)
    private Long userId;
    @JsonView(Views.Public.class)
    private String user;

    public CommentDto(Comment comment) {
        BeanUtils.copyProperties(comment, this);
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.user = comment.getUser().getName();
    }
}
