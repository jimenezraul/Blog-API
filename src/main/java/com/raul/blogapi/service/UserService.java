package com.raul.blogapi.service;

import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO updateUser(Long id, UserDTO user);
    UserDTO addRoleToUser(Long id, RoleDTO role);
    UserDTO removeRoleFromUser(Long id, Long roleId);
    void deleteUser(Long id);

    boolean userExists(String username);

    void createUser(UserDetails user);
    void verifyUser(Long id);

    UserDetails loadUserByUsername(String username);
    UserDTO getUserByEmail(String email);
}
