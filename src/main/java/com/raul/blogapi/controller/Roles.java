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

import java.net.URI;
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
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/roles")
    public ResponseEntity<Long> createRole(@RequestBody RoleDTO role) {
        RoleDTO roleDTO = roleService.createRole(role);
        URI location = URI.create(String.format("/roles/%s", roleDTO.getId()));

        return ResponseEntity.created(location).body(roleDTO.getId());
    }

    @GetMapping("/roles/{id}")
    @JsonView(Views.Private.class)
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        ResponseEntity<RoleDTO> role = ResponseEntity.of(Optional.ofNullable(roleService.getRoleById(id)));
        return role;
    }

    @PutMapping("/roles/user/{id}")
    public ResponseEntity<UserDTO> addRoleToUser(@PathVariable Long id, @RequestBody RoleDTO role) {
        return ResponseEntity.ok(userService.addRoleToUser(id, role));
    }

    @DeleteMapping("/roles/user/{id}/role/{roleId}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
        userService.removeRoleFromUser(id, roleId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleDTO role) {
        return ResponseEntity.ok(roleService.updateRole(id, role));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

}
