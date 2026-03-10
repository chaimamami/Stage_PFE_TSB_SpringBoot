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
@Table(name = "RF_MENU", schema = "IBANKT")
@IdClass(RfMenuId.class)
public class RfMenu implements Serializable {

    @Id
    @Column(name = "MENU", nullable = false)
    private String menu;

    @Id
    @Column(name = "COD_LAN", nullable = false)
    private String codLan;

    @Column(name = "LIB_MENU", nullable = false)
    private String libMenu;

    @Column(name = "LIB_FORM")
    private String libForm;

    @Column(name = "LIB_REPO")
    private String libRepo;

    @Column(name = "USE_MOD")
    private String useMod;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_MOD")
    private Date datMod;

    @Temporal(TemporalType.DATE)
    @Column(name = "DAT_CRE", nullable = false)
    private Date datCre;

    @Column(name = "USE_CRE", nullable = false)
    private String useCre;

    @Column(name = "MENU_PERE")
    private String menuPere;

    @Column(name = "COD_APP", nullable = false)
    private String codApp;

    @PrePersist
    public void prePersist() {
        if (datCre == null) datCre = new Date();
        if (useCre == null || useCre.isBlank()) useCre = "SYSTEM";
    }

    @PreUpdate
    public void preUpdate() {
        datMod = new Date();
        if (useMod == null || useMod.isBlank()) useMod = "SYSTEM";
    }
}