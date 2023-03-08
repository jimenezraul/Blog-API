package com.raul.blogapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "comments")
public class Post {
    @Id
    @GeneratedValue
    @JsonView(Views.Public.class)
    private Long id;

    @Size(min = 10, message = "Description should have at least 10 characters")
    @JsonView(Views.Public.class)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonView(Views.Public.class)
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonView(Views.Public.class)
    private Collection<Comment> comments = new ArrayList<>();

    public Post(Long postId) {
        this.id = postId;
    }

}
