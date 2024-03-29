package com.raul.blogapi.repository;

import com.raul.blogapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAll();

    Role findByName(String name);

}
