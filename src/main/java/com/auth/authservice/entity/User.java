package com.auth.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @PrimaryKey
    private UUID userId;

    private String email;
    private String password;
    private Instant createdAt;
}
