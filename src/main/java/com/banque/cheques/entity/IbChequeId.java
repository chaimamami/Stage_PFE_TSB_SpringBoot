package com.banque.cheques.entity;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class IbChequeId implements Serializable {
    private String numCpt;
    private BigDecimal numChq;
}