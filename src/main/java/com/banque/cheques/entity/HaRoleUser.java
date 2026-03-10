package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "HA_ROLE_USER", schema = "IBANK")
public class HaRoleUser implements Serializable {

    @EmbeddedId
    private HaRoleUserId id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DEBUT", nullable = false)
    private Date datDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FIN")
    private Date datFin;

    @PrePersist
    public void prePersist() {
        if (datDebut == null) datDebut = new Date();
    }
}