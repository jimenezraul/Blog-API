package com.raul.blogapi.service.ServiceImpl;
import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.dto.UserDTO;
import com.raul.blogapi.error.UserNotFoundException;
import com.raul.blogapi.model.Comment;
import com.raul.blogapi.model.Post;
import com.raul.blogapi.model.Role;
import com.raul.blogapi.model.User;
import com.raul.blogapi.repository.CommentRepository;
import com.raul.blogapi.repository.PostRepository;
import com.raul.blogapi.repository.RoleRepository;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserDetailsManager, UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

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
        userToUpdate.setIsEmailVerified(userDto.getIsEmailVerified());
        userToUpdate.setUpdatedAt();
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
        List<Post> posts = postRepository.findByUserId(id);
        List<Comment> comments = commentRepository.findByUserId(id);

        postRepository.deleteAll(posts);
        commentRepository.deleteAll(comments);
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
        User userToCreate = (User) user;

        Boolean userExists = userRepository.existsByUsernameOrEmail(userToCreate.getUsername(), userToCreate.getEmail());

        if(userExists){
            throw new IllegalStateException("Username or email already exists");
        }

        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        userToCreate.getRoles().add(role);

        userToCreate.setCreatedAt();
        userToCreate.setUpdatedAt();

        userRepository.save(userToCreate);
    }

    @Override
    public String saveUser(UserDetails user) {
        User userToCreate = (User) user;

        Boolean userExists = userRepository.existsByUsernameOrEmail(userToCreate.getUsername(), userToCreate.getEmail());

        if(userExists){
            return "Username or email already exists";
        }

        userToCreate.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        userToCreate.getRoles().add(role);

        userToCreate.setCreatedAt();
        userToCreate.setUpdatedAt();

        userRepository.save(userToCreate);
        return "Account created successfully";
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

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        return new UserDTO(user);
    }

    @Override
    public void removeRolesFromUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getRoles().clear();
        userRepository.save(user);
    }
}

