package com.raul.blogapi.service;

import com.raul.blogapi.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto createRole(RoleDto role);
    RoleDto getRoleById(Long id);
    RoleDto updateRole(Long id, RoleDto role);
    void deleteRole(Long id);

}
