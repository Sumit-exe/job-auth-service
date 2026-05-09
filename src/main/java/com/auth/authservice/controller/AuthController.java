package com.auth.authservice.controller;

import com.auth.authservice.dto.AuthRequest;
import com.auth.authservice.dto.AuthResponse;
import com.auth.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public Mono<AuthResponse> register(@RequestBody AuthRequest r) {
        return service.register(r.getEmail(), r.getPassword());
    }

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest r) {
        return service.login(r.getEmail(), r.getPassword());
    }

    @PostMapping("/refresh")
    public Mono<AuthResponse> refresh(@RequestBody Map<String,String> req) {
        return service.refresh(req.get("refreshToken"));
    }
}