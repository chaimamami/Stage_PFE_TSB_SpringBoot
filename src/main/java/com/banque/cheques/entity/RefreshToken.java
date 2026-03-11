package com.banque.cheques.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REFRESH_TOKEN", schema = "IBANK")
@Getter
@Setter
@SequenceGenerator(
        name = "refresh_token_seq_gen",
        sequenceName = "IBANK.REFRESH_TOKEN_SEQ",
        allocationSize = 1
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq_gen")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 100)
    private String username;

    @Column(name = "TOKEN", nullable = false, length = 2000, unique = true)
    private String token;

    @Column(name = "JTI", nullable = false, length = 255, unique = true)
    private String jti;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "REVOKED", nullable = false)
    private boolean revoked = false;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
}