package com.banque.cheques.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "IB_CHEQUIER", schema = "IBANK")
@IdClass(IbChequierId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IbChequier {

    @Id
    @Column(name = "NUM_CPT", length = 20, nullable = false)
    private String numCpt;

    @Id
    @Column(name = "NUM_DEB", nullable = false)
    private BigDecimal numDeb;

    @Column(name = "NUM_SEQ", nullable = false)
    private Long numSeq;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_JOU", nullable = false)
    private Date datJou;

    @Column(name = "COD_UG", length = 10, nullable = false)
    private String codUg;

    @Column(name = "NBR_CHQ", nullable = false)
    private Integer nbrChq;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DEL")
    private Date datDel;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_REC")
    private Date datRec;

    @Column(name = "COD_STA", length = 4, nullable = false)
    private String codSta;

    @Column(name = "USE_CRE", length = 30)
    private String useCre;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_CRE")
    private Date datCre;

    @Column(name = "USE_MOD", length = 30)
    private String useMod;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_MOD")
    private Date datMod;

    @Column(name = "USE_ANN", length = 30)
    private String useAnn;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_ANN")
    private Date datAnn;

    @Column(name = "COD_NIV")
    private BigDecimal codNiv;

    @Column(name = "LIB_FOR", length = 500)
    private String libFor;

    @Column(name = "USE_FOR", length = 30)
    private String useFor;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FOR")
    private Date datFor;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FIN_VAL")
    private Date datFinVal;
}