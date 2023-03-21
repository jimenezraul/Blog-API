package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.User;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonView(Views.Public.class)
    private Long id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    @NonNull
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private String username;
    @NonNull
    @JsonView(Views.Private.class)
    private LocalDate birthDate;
    @JsonView(Views.Private.class)
    @NonNull
    private String email;
    private String password;
    @JsonView(Views.Public.class)
    private String imageUrl;
    @JsonView(Views.Private.class)
    private Boolean isEmailVerified;
    @JsonView(Views.Public.class)
    Long numberOfPosts = 0L;
    @JsonView(Views.Private.class)
    private Collection<RoleDTO> roles = new ArrayList<>();
    @JsonView(Views.Private.class)
    private Collection<RefreshTokensDTO> refreshTokens = new ArrayList<>();

    @JsonView(Views.Private.class)
    private LocalDateTime created_at;
    @JsonView(Views.Private.class)
    private LocalDateTime updated_at;

    public UserDTO(User savedUser) {
        this.id = savedUser.getId();
        this.name = savedUser.getName();
        this.username = savedUser.getUsername();
        this.isEmailVerified = savedUser.getIsEmailVerified();
        this.birthDate = savedUser.getBirthDate();
        this.roles = savedUser.getRoles().stream().map(RoleDTO::new).toList();
        this.numberOfPosts = savedUser.getPosts().stream().count();
        this.email = savedUser.getEmail();
        this.created_at = savedUser.getCreated_at();
        this.updated_at = savedUser.getUpdated_at();
        this.imageUrl = savedUser.getImageUrl() == null ? "https://i.imgur.com/3ZQZ9Zm.png" : savedUser.getImageUrl();
        this.refreshTokens = savedUser.getRefreshTokens().stream().map(RefreshTokensDTO::new).toList();
    }


}
