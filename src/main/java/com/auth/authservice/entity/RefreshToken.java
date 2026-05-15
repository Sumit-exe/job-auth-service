package com.auth.authservice.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("refresh_tokens")
@Data
public class RefreshToken {

    @PrimaryKey
    private String token;

    @Column("user_id")
    private UUID userId;
    private Instant expiry;
    private Boolean revoked;
}
