package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @JsonView(Views.Public.class)
    private Long id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    @NonNull
    @JsonView(Views.Public.class)
    private String name;
    @NonNull
    @JsonView(Views.Private.class)
    private LocalDate birthDate;
    @JsonView(Views.Public.class)
    Collection<PostDto> posts = new ArrayList<>();
    @JsonView(Views.Public.class)
    Long numberOfPosts = 0L;
    @JsonView(Views.Private.class)
    private Collection<RoleDto> roles = new ArrayList<>();

    public UserDto(User savedUser) {
        this.id = savedUser.getId();
        this.name = savedUser.getName();
        this.birthDate = savedUser.getBirthDate();
        this.posts = savedUser.getPosts().stream().map(PostDto::new).toList();
        this.roles = savedUser.getRoles().stream().map(RoleDto::new).toList();
        this.numberOfPosts = savedUser.getPosts().stream().count();
    }
}
