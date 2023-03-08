package com.raul.blogapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "user_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    @Column(name = "name", nullable = false)
    private String name;
    @Past(message = "Birth date should be in the past")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Size(min = 8 , message = "Password should have at least 8 characters")
    @Column(name = "password", nullable = false)
    private String password;
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
//    private Collection<Post> posts = new ArrayList<>();
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Collection<Role> roles = new ArrayList<>();

    public User(Long userId) {
        this.id = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
//                ", posts=" + posts +
//                ", roles=" + roles +
                '}';
    }
}