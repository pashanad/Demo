package com.example.demo.repositories;

import com.example.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(String roleName);
}
