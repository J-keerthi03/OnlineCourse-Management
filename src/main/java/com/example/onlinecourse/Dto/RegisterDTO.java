package com.example.onlinecourse.Dto;

import com.example.onlinecourse.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
}