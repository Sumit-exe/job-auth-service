package com.auth.authservice.service;

import com.auth.authservice.dto.AuthResponse;
import com.auth.authservice.entity.RefreshToken;
import com.auth.authservice.entity.User;
import com.auth.authservice.repository.RefreshTokenRepository;
import com.auth.authservice.repository.UserRepository;
import com.auth.authservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public Mono<AuthResponse> register(String email, String password) {

        return Mono.fromCallable(() -> userRepo.findByEmail(email))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(existing -> {
                    if (existing.isPresent()) {
                        return Mono.error(new RuntimeException("User exists"));
                    }

                    User user = new User(
                            UUID.randomUUID(),
                            email,
                            encoder.encode(password),
                            Instant.now()
                    );

                    return Mono.fromCallable(() -> userRepo.save(user))
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(saved -> generateTokens(saved.getUserId()));
                });
    }

    public Mono<AuthResponse> login(String email, String password) {

        return Mono.fromCallable(() -> userRepo.findByEmail(email))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optUser -> {

                    if (optUser.isEmpty()) return Mono.error(new RuntimeException("User not Found"));

                    User user = optUser.get();

                    return Mono.fromCallable(() -> encoder.matches(password, user.getPassword()))
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(match -> {
                                if (!match) {
                                    return Mono.error(new RuntimeException("Invalid Password"));
                                }
                                return generateTokens(user.getUserId());
                            });
                });
    }

    public Mono<AuthResponse> refresh(String refreshToken) {

        return Mono.fromCallable(() -> refreshRepo.findById(refreshToken))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optToken -> {

                    if (optToken.isEmpty()) {
                        return Mono.error(new RuntimeException("Invalid refresh"));
                    }

                    RefreshToken token = optToken.get();

                    if (token.getRevoked() || token.getExpiry().isBefore(Instant.now())) {
                        return Mono.error(new RuntimeException("Token Expired"));
                    }

                    token.setRevoked(true);

                    return Mono.fromCallable(() -> refreshRepo.save(token))
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(saved -> generateTokens(saved.getUserId()));
                });
    }

    private Mono<AuthResponse> generateTokens(UUID userId) {

        return Mono.fromCallable(() -> {

            String access = jwt.generateAccessToken(userId.toString());
            String refresh = UUID.randomUUID().toString();

            RefreshToken rt = new RefreshToken();
            rt.setToken(refresh);
            rt.setUserId(userId);
            rt.setExpiry(Instant.now().plus(Duration.ofDays(7)));
            rt.setRevoked(false);

            refreshRepo.save(rt);

            return new AuthResponse(access, refresh);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}