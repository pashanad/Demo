package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.models.UserDTO;
import com.example.demo.repositories.UserRepositories;
import com.example.demo.services.UserDBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDBService userDBService;

    @Autowired
    private UserRepositories userRepositories;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepositories.deleteAll();

        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setName("Test");
        testUser.setLastName("User");
        testUser.setAge(30);
        userDBService.save(testUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/userlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(testUser.getUsername()))
                .andExpect(jsonPath("$[0].name").value(testUser.getName()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/user/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testUser.getUsername()))
                .andExpect(jsonPath("$.name").value(testUser.getName()));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Updated Name");
        userDTO.setLastName("Updated LastName");
        userDTO.setAge(35);

        mockMvc.perform(patch("/user/{id}", testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\",\"lastName\":\"Updated LastName\",\"age\":35}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.lastName").value("Updated LastName"))
                .andExpect(jsonPath("$.age").value(35));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/{id}", testUser.getId()))
                .andExpect(status().isOk());
    }
}