package com.raul.blogapi.service.UserServiceImpl;

import com.raul.blogapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

public class UserServiceImpl {
    @Service
    public class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;

        @Override
        public List<UserDto> getAllUsers() {
            return  userRepository.findAll()
                    .stream()
                    .map(user -> toDto(user))
                    .collect(Collectors.toList());
        }

        @Override
        public UserDto createUser(UserDto userDto) {
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
        public UserDto getUserById(Long id) {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            return new UserDto(user);
        }

        @Override
        public UserDto updateUser(Long id, UserDto user) {
            User userToUpdate = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            userToUpdate.setName(user.getName());
            userToUpdate.setBirthDate(user.getBirthDate());
            return new UserDto(userRepository.save(userToUpdate));
        }

        @Override
        public UserDto addRoleToUser(Long id, RoleDto role) {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
            user.getRoles().add(new Role(role));
            return new UserDto(userRepository.save(user));
        }

        @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }

        private User convertToEntity(UserDto userDto) {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            return user;
        }

        private UserDto toDto(User user) {
            return new UserDto(user);
        }

    }

}
