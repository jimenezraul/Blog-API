package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.UserDto;
import com.raul.blogapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class Users {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<UserDto>> retrieveAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }
    @JsonView(Views.Public.class)
    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/me/{id}")
    @JsonView(Views.Private.class)
    public UserDto getMe(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping("/users")
    public Long createUser(@Valid @RequestBody UserDto user) {
        UserDto userDto = service.createUser(user);
        return userDto.getId();
    }

    @PutMapping("/users/{id}")
    @JsonView(Views.Public.class)
    public UserDto updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

}