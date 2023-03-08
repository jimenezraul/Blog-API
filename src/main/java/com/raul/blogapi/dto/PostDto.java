package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.Post;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @JsonView(Views.Public.class)
    private Long id;
    @Size(min = 10, message = "Description should have at least 10 characters")
    @JsonView(Views.Public.class)
    private String description;
    @NonNull
    @JsonView(Views.Public.class)
    private Long userId;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonView(Views.Public.class)
    private String userName;
    @JsonView(Views.Public.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    // get users with the same id too
    private Collection<CommentDto> comments;

    public PostDto(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.userId = post.getUser().getId();
        this.userName = post.getUser().getName();
        this.comments = post.getComments().stream().map(CommentDto::new).toList();
    }


}

