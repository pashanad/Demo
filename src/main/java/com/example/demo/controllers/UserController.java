package com.example.demo.controllers;

import com.example.demo.configs.CustomUserDetails;
import com.example.demo.models.User;
import com.example.demo.models.UserDTO;
import com.example.demo.services.UserDBService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserDBService userDBService;


    @GetMapping("/userlist")
    public List<User> getAllUsers(){
        return userDBService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User showId(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user= userDBService.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new SecurityException("Access denied: only ADMIN can view user");
        }
        return user;
    }

    @GetMapping("/user/me")
    public User showMe(@AuthenticationPrincipal CustomUserDetails customUserDetails){
            return customUserDetails.getUser();
    }

    @PatchMapping("/user/{id}")
    public User update(@RequestBody @Valid UserDTO newUser, @PathVariable Long id){
       return userDBService.updateUser(id, newUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userDBService.removeUserById(id);
    }
}
