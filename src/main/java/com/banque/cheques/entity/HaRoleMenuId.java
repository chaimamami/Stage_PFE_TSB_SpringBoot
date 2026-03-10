package com.banque.cheques.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class HaRoleMenuId implements Serializable {

    @Column(name = "MENU")
    private String menu;

    @Column(name = "COD_LAN")
    private String codLan;

    @Column(name = "ROLE")
    private String role;
}