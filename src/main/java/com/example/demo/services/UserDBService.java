package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepositories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDBService{

    private final UserRepositories userRepositories;

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepositories.findAll();
    }

    @Transactional
    public void save(User user){
        userRepositories.save(user);
    }
}
