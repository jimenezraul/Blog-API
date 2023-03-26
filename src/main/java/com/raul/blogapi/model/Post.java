package com.raul.blogapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Title should have at least 5 characters")
    @Column(nullable = false)
    private String title;

    @Size(min = 10, message = "Description should have at least 10 characters")
    @Column(nullable = false, length = 5000)
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Collection<Comment> comments = new ArrayList<>();
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public Post(Long postId) {
        this.id = postId;
    }

    public Post(String s, String s1, User user1) {
        this.title = s;
        this.body = s1;
        this.user = user1;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setUpdatedAt() {
        this.updated_at = LocalDateTime.now();
    }
}
