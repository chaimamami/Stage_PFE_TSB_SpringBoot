package com.banque.cheques.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BLACKLISTED_TOKEN", schema = "IBANK")
@Getter
@Setter
@SequenceGenerator(
        name = "blacklisted_token_seq_gen",
        sequenceName = "IBANK.BLACKLISTED_TOKEN_SEQ",
        allocationSize = 1
)
public class BlacklistedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blacklisted_token_seq_gen")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOKEN", nullable = false, length = 2000, unique = true)
    private String token;

    @Column(name = "JTI", nullable = false, length = 255, unique = true)
    private String jti;

    @Column(name = "USERNAME", nullable = false, length = 100)
    private String username;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}