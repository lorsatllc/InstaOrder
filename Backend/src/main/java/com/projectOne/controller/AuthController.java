package com.projectOne.controller;

import com.projectOne.dto.request.auth.LoginRequest;
import com.projectOne.dto.response.auth.AuthResponse;
import com.projectOne.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);

    }
}
