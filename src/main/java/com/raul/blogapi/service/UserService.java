package com.raul.blogapi.service;

import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO user);
    UserDTO addRoleToUser(Long id, RoleDTO role);
    UserDTO removeRoleFromUser(Long id, Long roleId);
    void deleteUser(Long id);

    void createUser(UserDetails user);

    String saveUser(UserDetails user);

    boolean userExists(String username);

    UserDTO getUserByEmail(String email);

    void removeRolesFromUser(Long id);
}
