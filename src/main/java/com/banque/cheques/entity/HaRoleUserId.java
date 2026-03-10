package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
@Data
public class HaRoleUserId implements Serializable {

    @Column(name = "ORA_UTI", length = 30, nullable = false)
    private String oraUti;

    @Column(name = "ROLE", length = 30, nullable = false)
    private String role;
}