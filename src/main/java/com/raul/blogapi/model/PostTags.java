package com.raul.blogapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "post_tag")
public class PostTags {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
