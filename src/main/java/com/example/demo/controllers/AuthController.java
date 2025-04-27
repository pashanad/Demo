package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.services.RoleDBService;
import com.example.demo.services.UserDBService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
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

//    @PostMapping("/custom-login")
//    public String loginPage(){
//        return "login";
//    }
}
