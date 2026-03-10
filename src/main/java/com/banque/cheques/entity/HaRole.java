package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "HA_ROLE", schema = "IBANK")
public class HaRole implements Serializable {

    @Id
    @Column(name = "ROLE", nullable = false, length = 30)
    private String role;

    @Column(name = "DES_ROLE", nullable = false, length = 100)
    private String desRole;
}