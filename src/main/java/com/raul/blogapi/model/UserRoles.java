package com.raul.blogapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoles {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
