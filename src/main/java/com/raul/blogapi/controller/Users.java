package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
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
    // preAuthorize is used to check if the user id is the same as the id in the path
    @PreAuthorize("#id == authentication.principal.id")
    public UserDTO getMe(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping("/users")
    public Long createUser(@Valid @RequestBody UserDTO user) {
        UserDTO userDto = service.createUser(user);
        return userDto.getId();
    }

    @PutMapping("/users/{id}")
    @JsonView(Views.Public.class)
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

}