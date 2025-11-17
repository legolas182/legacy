package com.legacy.legacy.service;

import com.legacy.legacy.dto.LoginRequestDTO;
import com.legacy.legacy.dto.LoginResponseDTO;
import com.legacy.legacy.dto.RegisterRequestDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
    LoginResponseDTO register(RegisterRequestDTO request);
}

