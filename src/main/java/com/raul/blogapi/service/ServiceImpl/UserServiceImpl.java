package com.raul.blogapi.service.ServiceImpl;

import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Role;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService, UserDetailsManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return  userRepository.findAll()
                .stream()
                .map(user -> toDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDto) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userToUpdate.setName(userDto.getName());
        userToUpdate.setBirthDate(userDto.getBirthDate());
        userToUpdate.setIsEmailVerified(userDto.getIsEmailVerified());
        return new UserDTO(userRepository.save(userToUpdate));
    }

    @Override
    public UserDTO addRoleToUser(Long id, RoleDTO role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getRoles().add(new Role(role));
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO removeRoleFromUser(Long id, Long roleId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getRoles().removeIf(role -> role.getId().equals(roleId));
        return new UserDTO(userRepository.save(user));
    }

    @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }

        private User convertToEntity(UserDTO userDto) {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            return user;
        }

        private UserDTO toDto(User user) {
            return new UserDTO(user);
        }

    @Override
    public void createUser(UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((User) user);
    }

    @Override
    public void verifyUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setVerified(true);
            userRepository.save(user);
        });
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("username {0} not found", username)
                ));
    }
}
