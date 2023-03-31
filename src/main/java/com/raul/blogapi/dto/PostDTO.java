package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.Tag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    @JsonView(Views.Public.class)
    private Long id;

    @JsonView(Views.Public.class)
    private String title;

    @JsonView(Views.Public.class)
    private String content;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonView(Views.Public.class)
    private String author;
    @JsonView(Views.Public.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Long numberOfComments;
    @JsonView(Views.Public.class)
    private LocalDateTime created_at;
    @JsonView(Views.Public.class)
    private LocalDateTime updated_at;

    @JsonView(Views.Public.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<String> tags;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getUser().getName();
        this.numberOfComments = post.getComments().stream().count();
        this.created_at = post.getCreatedAt();
        this.updated_at = post.getUpdatedAt();
        this.tags = new ArrayList<>();
        if (post.getTags() != null) {
            for (Tag tag : post.getTags()) {
                this.tags.add(tag.getName());
            }
        }
    }

}

