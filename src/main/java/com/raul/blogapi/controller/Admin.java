package com.raul.blogapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.model.User;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class Admin {

    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    @JsonView(Views.Private.class)
    public String admin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authUser = (User) auth.getPrincipal();

        UserDTO user = userService.getUserById(authUser.getId());

        System.out.println(user);

        return auth.getPrincipal().toString();
    }
}
