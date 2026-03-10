package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RF_STATUT", schema = "IBANK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RfStatut {

    @Id
    @Column(name = "COD_STA")
    private String codSta;

    @Column(name = "LIB_STA")
    private String libSta;
}