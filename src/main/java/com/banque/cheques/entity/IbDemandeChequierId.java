package com.banque.cheques.entity;

import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class IbDemandeChequierId implements Serializable {
    private String codUg;
    private Date datJou;
    private Long numSeq;
}