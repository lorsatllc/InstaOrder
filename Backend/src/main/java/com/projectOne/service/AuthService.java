package com.projectOne.service;

import com.projectOne.dto.request.auth.LoginRequest;
import com.projectOne.dto.response.auth.AuthResponse;
import com.projectOne.entity.Customer;
import com.projectOne.repository.CustomerRepository;
import com.projectOne.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest request) {

        Customer customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(customer.getEmail(), customer.getRole().name());

        return new AuthResponse(token);
    }

}
