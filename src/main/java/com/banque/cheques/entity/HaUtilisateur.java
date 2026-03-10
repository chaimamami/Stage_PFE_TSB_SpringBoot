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
@Table(name = "HA_UTILISATEUR", schema = "IBANK")
public class HaUtilisateur implements Serializable {

    @Id
    @Column(name = "ORA_UTI", nullable = false, length = 30)
    private String oraUti;

    @Column(name = "NOM_UTI", nullable = false, length = 30)
    private String nomUti;

    @Column(name = "COD_UG", length = 10)
    private String codUg;

    @Column(name = "COD_NIV")
    private Integer codNiv;

    @Column(name = "AUT_UG", nullable = false, length = 1)
    private String autUg; // 'O' ou 'N'

    @Column(name = "OPE_DEP", nullable = false, length = 1)
    private String opeDep; // 'O' ou 'N'

    @Column(name = "PCT_COM", nullable = false)
    private Integer pctCom; // 0..100

    @Column(name = "AUT_DV", nullable = false, length = 240)
    private String autDv; // souvent 'N' ou liste

    @Column(name = "FLG_SUP", nullable = false, length = 1)
    private String flgSup; // 'O' ou 'N'

    @Column(name = "USE_CRE", nullable = false, length = 240)
    private String useCre;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_CRE", nullable = false)
    private Date datCre;

    @Column(name = "MATR_UTI", length = 30)
    private String matrUti;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DEB", nullable = false)
    private Date datDeb;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FIN")
    private Date datFin;

    @Temporal(TemporalType.DATE)
    @Column(name = "HEU_DEB")
    private Date heuDeb;

    @Temporal(TemporalType.DATE)
    @Column(name = "HEU_FIN")
    private Date heuFin;

    @Column(name = "GUI_PAY", nullable = false, length = 1)
    private String guiPay; // 'O' ou 'N'

    @Column(name = "FLG_CHA_CLI", length = 1)
    private String flgChaCli; // 'O' ou 'N'

    @Column(name = "FLG_CHA_UTI", nullable = false, length = 1)
    private String flgChaUti; // 'O' ou 'N'

    @Column(name = "COD_CAI", length = 5)
    private String codCai;

    @PrePersist
    public void prePersist() {
        if (datCre == null) datCre = new Date();
        if (datDeb == null) datDeb = new Date();
        if (autUg == null) autUg = "N";
        if (opeDep == null) opeDep = "N";
        if (pctCom == null) pctCom = 0;
        if (autDv == null) autDv = "N";
        if (flgSup == null) flgSup = "N";
        if (guiPay == null) guiPay = "N";
        if (flgChaUti == null) flgChaUti = "N";
    }
}