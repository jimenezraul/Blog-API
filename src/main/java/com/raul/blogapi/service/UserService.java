package com.raul.blogapi.service;

import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO user);
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO user);
    UserDTO addRoleToUser(Long id, RoleDTO role);
    UserDTO removeRoleFromUser(Long id, Long roleId);
    void deleteUser(Long id);
}
