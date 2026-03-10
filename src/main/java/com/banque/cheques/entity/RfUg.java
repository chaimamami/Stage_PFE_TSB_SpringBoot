package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RF_UG", schema = "IBANK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RfUg {

    @Id
    @Column(name = "COD_UG", length = 10, nullable = false)
    private String codUg;

    @Column(name = "LIB_UG", length = 100, nullable = false)
    private String libUg;

    @Column(name = "COD_TYP_UG", length = 5, nullable = false)
    private String codTypUg;

    @Column(name = "ADR_UG", length = 250)
    private String adrUg;

    @Column(name = "COD_VIL", length = 5)
    private String codVil;

    @Column(name = "COD_ZON_UG", length = 5)
    private String codZonUg;

    @Column(name = "TEL_UG", length = 20)
    private String telUg;

    @Column(name = "FAX_UG", length = 20)
    private String faxUg;

    @Column(name = "CHE_UG", length = 150)
    private String cheUg;

    @Column(name = "TEL_CHE_UG", length = 20)
    private String telCheUg;

    @Column(name = "COD_UG_CMP", length = 10)
    private String codUgCmp;

    @Column(name = "COD_AGE", length = 10)
    private String codAge;

    @Column(name = "COD_BQE", length = 10)
    private String codBqe;

    @Column(name = "ACT_UG")
    private Integer actUg;
}