package com.legacy.legacy.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String fullName;
    private String username;
    private String email;
    private String password;
}

