package com.example.demo.controllers;

import com.example.demo.repositories.UserRepositories;
import com.example.demo.services.UserDBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    UserRepositories userRepositories;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepositories.deleteAll();
    }

    @Test
    void testNewUser() throws Exception {
        String userJson = """
                    {
                       "username": "user123",
                       "password": "user",
                       "name": "John",
                       "lastName": "Doe",
                       "age": 25
                    }
                """;

        mockMvc.perform(post("/auth/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user123"))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(25));
    }
}