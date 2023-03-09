package com.raul.blogapi.service.UserServiceImpl;

import com.fasterxml.jackson.annotation.JsonView;
import com.raul.blogapi.controller.Views;
import com.raul.blogapi.dto.RoleDTO;
import com.raul.blogapi.model.Role;
import com.raul.blogapi.repository.RoleRepository;
import com.raul.blogapi.repository.UserRepository;
import com.raul.blogapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleDTO::new).collect(Collectors.toList());
    }

    @Override
    public RoleDTO createRole(RoleDTO role) {
        Role roleModel = convertToEntity(role);
        return new RoleDTO(roleRepository.save(roleModel));
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        return roleRepository.findById(id).map(RoleDTO::new).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public RoleDTO updateRole(Long id, RoleDTO role) {
        return  roleRepository.findById(id).map(roleModel -> {
            roleModel.setName(role.getName());
            return new RoleDTO(roleRepository.save(roleModel));
        }).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    @JsonView(Views.Public.class)
    public void deleteRole(Long id) {
        userRepository.findAll().forEach(user -> {
            if (user.getRoles().stream().anyMatch(role -> role.getId().equals(id))) {
                user.getRoles().removeIf(role -> role.getId().equals(id));
                userRepository.save(user);
            }
        });
        roleRepository.deleteById(id);
    }

    private Role convertToEntity(RoleDTO role) {
        Role roleModel = new Role();
        roleModel.setName(role.getName());
        return roleModel;
    }
}
