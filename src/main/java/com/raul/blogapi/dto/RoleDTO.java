package com.raul.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    @JsonView(Views.Private.class)
    private Long id;
    @JsonView(Views.Private.class)
    private String name;
    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleDTO(String roleUser) {
        this.name = roleUser;
    }
}

