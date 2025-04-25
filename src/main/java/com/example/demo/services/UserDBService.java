package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepositories;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDBService{

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepositories.findAll();
    }

    @Transactional
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepositories.save(user);
    }

    @Transactional
    public Optional<User> findUserById(Long id){
        return userRepositories.findUserById(id);
    }

    @Transactional
    public void updateUser(Long id, User newUser){
        User user = findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        user.setName(newUser.getName());
        user.setLastName(newUser.getLastName());
        user.setAge(newUser.getAge());
        System.out.println("UJsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        userRepositories.saveAndFlush(user);
    }

    @Transactional
    public void removeUserById(Long id){
        userRepositories.removeUserById(id);
    }
}
