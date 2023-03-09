package com.raul.blogapi.service;

import com.raul.blogapi.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO createRole(RoleDTO role);
    RoleDTO getRoleById(Long id);
    RoleDTO updateRole(Long id, RoleDTO role);
    void deleteRole(Long id);

}
