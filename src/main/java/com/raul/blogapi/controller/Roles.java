package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.service.RoleService;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class Roles {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @GetMapping("/roles")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/roles")
    public Long createRole(@RequestBody RoleDTO role) {
        return roleService.createRole(role).getId();
    }

    @GetMapping("/roles/{id}")
    @JsonView(Views.Private.class)
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        ResponseEntity<RoleDTO> role = ResponseEntity.of(Optional.ofNullable(roleService.getRoleById(id)));
        System.out.println(role);
        return role;
    }

    @PutMapping("/roles/user/{id}")
    public void addRoleToUser(@PathVariable Long id, @RequestBody RoleDTO role) {
        userService.addRoleToUser(id, role);
    }

    @DeleteMapping("/roles/user/{id}/role/{roleId}")
    public void deleteRoleFromUser(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
        userService.removeRoleFromUser(id, roleId);
    }

    @PutMapping("/roles/{id}")
    public RoleDTO updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
        return roleService.updateRole(id, role);
    }

    @DeleteMapping("/roles/user/{id}")
    public void deleteRoleFromUser(@PathVariable Long id, @RequestBody RoleDTO role) {
        System.out.println(role);
        Optional<UserDTO> users = Optional.ofNullable(userService.getUserById(id));
        if (users.isPresent()) {
            UserDTO user = users.get();
            Collection<RoleDTO> roles = user.getRoles();
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
