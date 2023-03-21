package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ROLE_USER')")
public class Users {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<UserDTO>> retrieveAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }
    @JsonView(Views.Public.class)
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/me/{id}")
    @JsonView(Views.Private.class)
    @PreAuthorize("#id == authentication.principal.id")
    public UserDTO getMe(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PutMapping("/users/{id}")
    @JsonView(Views.Public.class)
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @JsonView(Views.Public.class)
    public void deleteUser(@PathVariable Long id) {
        System.out.println(id);
        service.deleteUser(id);
    }

    @GetMapping("/users/email/{email}")
    @JsonView(Views.Public.class)
    public UserDTO getUserByEmail(@PathVariable String email) {
        return service.getUserByEmail(email);
    }

}