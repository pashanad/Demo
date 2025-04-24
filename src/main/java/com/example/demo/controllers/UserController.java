package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UserDBService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserDBService service;


    @GetMapping("/userlist")
    public String getAllUsee(@ModelAttribute("list") List<User> list){
        list = service.getAllUsers();
        return "userlist";
    }
}
