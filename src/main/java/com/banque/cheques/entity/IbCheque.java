package com.banque.cheques.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "IB_CHEQUE", schema = "IBANK")
@IdClass(IbChequeId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IbCheque {

    @Id
    @Column(name = "NUM_CPT", length = 20, nullable = false)
    private String numCpt;

    @Id
    @Column(name = "NUM_CHQ", nullable = false)
    private BigDecimal numChq;

    @Column(name = "NUM_DEB", nullable = false)
    private BigDecimal numDeb;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_REG")
    private Date datReg;

    @Column(name = "MNT_REG")
    private BigDecimal mntReg;

    @Column(name = "COD_STA", length = 4, nullable = false)
    private String codSta;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_DEL")
    private Date datDel;

    @Column(name = "MNT_PLF")
    private BigDecimal mntPlf;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_FIN_VAL")
    private Date datFinVal;

    @Column(name = "CLE_SECURITE", length = 16)
    private String cleSecurite;
}