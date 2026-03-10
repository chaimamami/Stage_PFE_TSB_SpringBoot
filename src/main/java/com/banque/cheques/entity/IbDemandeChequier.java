package com.banque.cheques.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "IB_DEMANDE_CHEQUIER", schema = "IBANK")
@IdClass(IbDemandeChequierId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IbDemandeChequier {

    @Id
    @Column(name = "COD_UG", length = 10, nullable = false)
    private String codUg;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_JOU", nullable = false)
    private Date datJou;

    @Id
    @Column(name = "NUM_SEQ", nullable = false)
    private Long numSeq;

    @Column(name = "COD_OPE", length = 10, nullable = false)
    private String codOpe;

    @Column(name = "NUM_CPT", length = 20, nullable = false)
    private String numCpt;

    @Column(name = "COD_TYP_CHQ", length = 5, nullable = false)
    private String codTypChq;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DDE")
    private Date datDde;

    @Column(name = "NBR_CHE", nullable = false)
    private Integer nbrChe = 1;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_EMI")
    private Date datEmi;

    @Column(name = "COD_STA", length = 4, nullable = false)
    private String codSta;

    @Column(name = "USE_CRE", length = 30, nullable = false)
    private String useCre;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_CRE", nullable = false)
    private Date datCre;
}