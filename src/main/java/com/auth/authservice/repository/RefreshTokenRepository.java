package com.auth.authservice.repository;

import com.auth.authservice.entity.RefreshToken;
import com.auth.authservice.entity.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends CassandraRepository<RefreshToken, String> {

    // Find all tokens for a user (useful for logout all sessions)
    List<RefreshToken> findByUserId(UUID userId);

    // Optional: fetch only active tokens
    List<RefreshToken> findByUserIdAndRevokedFalse(UUID userId);
}