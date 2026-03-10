package com.banque.cheques.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "HA_ROLE_MENU", schema = "IBANK")
@Getter
@Data
@Setter

public class HaRoleMenu {

    @EmbeddedId
    private HaRoleMenuId id;

    public HaRoleMenu() {
        this.id = new HaRoleMenuId();
    }

    // =========================
    // GETTERS SIMPLIFIÉS
    // =========================

    public String getMenu() {
        return id != null ? id.getMenu() : null;
    }

    public String getCodLan() {
        return id != null ? id.getCodLan() : null;
    }

    public String getRole() {
        return id != null ? id.getRole() : null;
    }

    // =========================
    // SETTERS SIMPLIFIÉS
    // =========================

    public void setMenu(String menu) {
        if (id == null) id = new HaRoleMenuId();
        id.setMenu(menu);
    }

    public void setCodLan(String codLan) {
        if (id == null) id = new HaRoleMenuId();
        id.setCodLan(codLan);
    }

    public void setRole(String role) {
        if (id == null) id = new HaRoleMenuId();
        id.setRole(role);
    }
}