package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "IB_COMPTE", schema = "IBANK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IbCompte {

    @Id
    @Column(name = "NUM_CPT", length = 20, nullable = false)
    private String numCpt;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_OUV_CPT", nullable = false)
    private Date datOuvCpt;

    @Column(name = "LIB_CPT", length = 100, nullable = false)
    private String libCpt;

    @Column(name = "RIB_CPT", length = 30, nullable = false)
    private String ribCpt;

    @Column(name = "COD_DEV", length = 5, nullable = false)
    private String codDev;

    @Column(name = "SLD_COU", nullable = false)
    private Double sldCou = 0.0;

    @Column(name = "TYP_CPT", length = 1, nullable = false)
    private String typCpt;

    @Column(name = "COD_PER", length = 20)
    private String codPer;

    @Column(name = "COD_UG", length = 10, nullable = false)
    private String codUg;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_JOU")
    private Date datJou;

    @Column(name = "NUM_SEQ")
    private Long numSeq;

    @Column(name = "COD_CAT_CPT", length = 10, nullable = false)
    private String codCatCpt;

    @Column(name = "COD_CPT", length = 20)
    private String codCpt;

    @Column(name = "MNT_PLA_DEC")
    private Double mntPlaDec;

    @Column(name = "MNT_BLO")
    private Double mntBlo;

    @Column(name = "COD_PRO", length = 10, nullable = false)
    private String codPro;

    @Column(name = "COD_STA", length = 4, nullable = false)
    private String codSta;

    @Column(name = "USE_CRE", length = 30, nullable = false)
    private String useCre;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_CRE", nullable = false)
    private Date datCre;
}