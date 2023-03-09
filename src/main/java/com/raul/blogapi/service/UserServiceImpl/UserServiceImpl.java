package com.raul.blogapi.service.UserServiceImpl;

import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.error.NullFieldException;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Role;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;

        @Override
        public List<UserDTO> getAllUsers() {
            return  userRepository.findAll()
                    .stream()
                    .map(user -> toDto(user))
                    .collect(Collectors.toList());
        }

        @Override
        public UserDTO createUser(UserDTO userDto) {
            if (userDto.getName() == null || userDto.getBirthDate() == null) {
                throw new NullFieldException("Name and birthdate cannot be null.");
            }

            User user = convertToEntity(userDto);
            User savedUser = userRepository.save(user);

            if (savedUser == null) {
                throw new UserNotFoundException("Something went wrong");
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.getId())
                    .toUri();
            return ResponseEntity.created(location).body(toDto(savedUser)).getBody();
        }

        @Override
        public UserDTO getUserById(Long id) {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            return new UserDTO(user);
        }

        @Override
        public UserDTO updateUser(Long id, UserDTO user) {
            User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            userToUpdate.setName(user.getName());
            userToUpdate.setBirthDate(user.getBirthDate());
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

}

