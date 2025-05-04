package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usertable")
@Builder
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The username must not be empty.")
    @Size(min = 6, max = 30, message = "Enter from 6 to 30 characters.")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "The password must not be empty.")
    @Column(name= "password")
    private String password;

    @NotEmpty(message = "The name must not be empty.")
    @Size(min = 2, max = 30, message = "Enter from 2 to 30 characters.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "The lastName must not be empty.")
    @Size(min = 2, max = 30, message = "Enter from 2 to 30 characters.")
    @Column(name = "lastname")
    private String lastName;

    @Min(value = 0, message = "Enter a positive number.")
    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();
}
