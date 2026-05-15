package com.auth.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
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
    @Column("user_id")
    private UUID userId;

    private String email;
    private String password;

    @Column("created_at")
    private Instant createdAt;
}
