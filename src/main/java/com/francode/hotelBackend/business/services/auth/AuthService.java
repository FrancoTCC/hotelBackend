package com.francode.hotelBackend.business.services.auth;

import com.francode.hotelBackend.presentation.dto.auth.JwtResponse;
import com.francode.hotelBackend.presentation.dto.auth.LoginRequest;
import com.francode.hotelBackend.presentation.dto.auth.SignupRequest;

public interface AuthService {
    boolean existsByEmail(String email);
    void registerUser(SignupRequest signUpRequest);
    JwtResponse authenticateUser(LoginRequest loginRequest);
}
