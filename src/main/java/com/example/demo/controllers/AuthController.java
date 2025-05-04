package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.RoleDBService;
import com.example.demo.services.UserDBService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserDBService userDBService;
    private final RoleDBService roleDBService;


    @PostMapping("/new")
    public User newUser(@RequestBody User user) {
        user.setRoles(roleDBService.findByRoleName("USER").stream().toList());
        userDBService.save(user);
        return user;
    }
}