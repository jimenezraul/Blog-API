package com.raul.blogapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

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
    @NonNull
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @NonNull
    @ToString.Exclude
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NonNull
    @ToString.Exclude
    private User user;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public void setCreatedAt() {
        this.created_at = LocalDateTime.now();
    }

    public void setUpdatedAt() {
        this.updated_at = LocalDateTime.now();
    }
}
