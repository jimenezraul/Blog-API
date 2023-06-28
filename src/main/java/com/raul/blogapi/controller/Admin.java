package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.PostDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.service.PostService;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "https://649c83fe6fe6f80d997f1350--illustrious-sprite-a61321.netlify.app")
public class Admin {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    @JsonView(Views.Private.class)
    public List<PostDTO> admin(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "50") int size) {

        List<PostDTO> posts = postService.getLatestPosts(page, size);
        return  posts;
    }
    @RequestMapping("/admin/all_users")
    @JsonView(Views.Private.class)
    public List<UserDTO> allUsers() {

        List<UserDTO> users = userService.getAllUsers();
        return  users;
    }

    @DeleteMapping("/admin/delete_user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.removeRolesFromUser(id);
        userService.deleteUser(id);
    }
}
