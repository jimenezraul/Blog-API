package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.RoleDto;
import com.raul.blogapi.dto.UserDto;
import com.raul.blogapi.service.RoleService;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class Roles {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/roles")
    public Long createRole(@RequestBody RoleDto role) {
        return roleService.createRole(role).getId();
    }

    @GetMapping("/roles/{id}")
    @JsonView(Views.Private.class)
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        ResponseEntity<RoleDto> role = ResponseEntity.of(Optional.ofNullable(roleService.getRoleById(id)));
        System.out.println(role);
        return role;
    }

    @PutMapping("/roles/user/{id}")
    public void addRoleToUser(@PathVariable Long id, @RequestBody RoleDto role) {
        userService.addRoleToUser(id, role);
    }

    @PutMapping("/roles/{id}")
    public RoleDto updateRole(@PathVariable Long id, @RequestBody RoleDto role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/roles/user/{id}")
    public void deleteRoleFromUser(@PathVariable Long id, @RequestBody RoleDto role) {
        System.out.println(role);
        Optional<UserDto> users = Optional.ofNullable(userService.getUserById(id));
        if (users.isPresent()) {
            UserDto user = users.get();
            Collection<RoleDto> roles = user.getRoles();
            roles.removeIf(r -> r.getId().equals(role.getId()));
            user.setRoles(roles);
            userService.updateUser(id, user);
        }
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

}
