package com.example.demo.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotEmpty(message = "The name must not be empty.")
    @Size(min = 2, max = 30, message = "Enter from 2 to 30 characters.")
    private String name;

    @NotEmpty(message = "The lastName must not be empty.")
    @Size(min = 2, max = 30, message = "Enter from 2 to 30 characters.")
    private String lastName;

    @Min(value = 0, message = "Enter a positive number.")
    private int age;
}