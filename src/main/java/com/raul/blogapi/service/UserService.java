package com.raul.blogapi.service;

import com.raul.blogapi.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto createUser(UserDto user);
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto user);
    UserDto addRoleToUser(Long id, RoleDto role);
    void deleteUser(Long id);
}
