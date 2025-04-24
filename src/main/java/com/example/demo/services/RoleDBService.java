package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.repositories.RolesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleDBService {

    private final RolesRepository rolesRepository;

    @Transactional
    public List<Role> findAll(){
        return rolesRepository.findAll();
    }

    @Transactional
    public Role findByRoleName(String roleName ){
        return rolesRepository.findByRoleName(roleName);
    }
}
