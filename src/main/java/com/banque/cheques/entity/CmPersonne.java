package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CM_PERSONNE", schema = "IBANK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CmPersonne {

    @Id
    @Column(name = "COD_PER", nullable = false, length = 20)
    private String codPer;


    @Column(name = "COD_UG", nullable = false, length = 10)
    private String codUg;


    @Column(name = "NOM_PER", nullable = false, length = 100)
    private String nomPer;


    @Column(name = "PRE_PER", length = 60)
    private String prePer;


    @Column(name = "TYP_PER", nullable = false, length = 1)
    private String typPer;


    @Column(name = "COD_STA", nullable = false, length = 4)
    private String codSta;


    @Column(name = "EMA_PER", length = 50)
    private String emaPer;


    @Column(name = "TEL1_PER", length = 20)
    private String tel1Per;


    @Column(name = "DAT_CRE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datCre;


    @Column(name = "USE_CRE", nullable = false, length = 30)
    private String useCre;

}