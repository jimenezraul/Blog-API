package com.raul.blogapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

@Entity(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @org.springframework.lang.NonNull
    @JsonView(Views.Public.class)
    @ToString.Exclude
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NonNull
    @JsonView(Views.Private.class)
    @ToString.Exclude
    private User user;

}
