package com.raul.blogapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@Entity(name = "user")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    @Column(name = "name", nullable = false)
    private String name;
    @NonNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
    private String imageUrl;

    private Boolean isEmailVerified = false;
    @Past(message = "Birth date should be in the past")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();
    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public User(Long userId) {
        this.id = userId;
    }

    public User(String name, String username, String email, String password, LocalDate birthDate) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", birthDate=" + birthDate +
                ", posts=" + posts +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setVerified(boolean b) {
        isEmailVerified = b;
    }

    public void setCreatedAt() {
        this.created_at = LocalDateTime.now();
    }

    public void setUpdatedAt() {
        this.updated_at = LocalDateTime.now();
    }
}

