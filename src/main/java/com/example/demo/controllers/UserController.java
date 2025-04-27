package com.example.demo.controllers;

import com.example.demo.configs.CustomUserDetails;
import com.example.demo.models.User;
import com.example.demo.models.UserDTO;
import com.example.demo.services.UserDBService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserDBService userDBService;


    @GetMapping("/userlist")
    public String getAllUsers(Model model){
        List<User> list = userDBService.getAllUsers();
        model.addAttribute("list", list);
        return "userList";
    }

    @GetMapping("/user/{id}")
    public String showId(Model model, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        User user= userDBService.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        model.addAttribute("user", user);

        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return "show";
        }
        return "userlist";
    }

    @GetMapping("/user/me")
    public String showMe(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model){

        boolean isAdmin = customUserDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = customUserDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_USER"));

        if (isAdmin || isUser) {
            model.addAttribute("user", customUserDetails.getUser());
            return "showMe";
        }
        return "user";
    }

    @GetMapping("/user/{id}/update")
    public String updateUser(Model model, @PathVariable Long id){
        User user = userDBService.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        model.addAttribute("user", user);
        return "update";
    }

    @PatchMapping("/user/{id}/update")
    public String update(@ModelAttribute ("user") @Valid UserDTO newUser, BindingResult bindingResult, @PathVariable Long id){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "update";
        }
           userDBService.updateUser(id, newUser);
        return "redirect:/user/{id}";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id){
        userDBService.removeUserById(id);
        return "redirect:/userlist";
    }
}
