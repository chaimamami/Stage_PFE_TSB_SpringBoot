package com.banque.cheques.repository;

import com.banque.cheques.entity.HaRoleMenu;
import com.banque.cheques.entity.HaRoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HaRoleMenuRepository extends JpaRepository<HaRoleMenu, HaRoleMenuId> {

    @Query(value = "SELECT COUNT(1) FROM IBANKT.RF_MENU WHERE MENU = :menu AND COD_LAN = :codLan", nativeQuery = true)
    int parentExists(@Param("menu") String menu, @Param("codLan") String codLan);

    // ✅ récupérer tous les menus autorisés pour un role
    // ROLE est dans EmbeddedId => id.role
    List<HaRoleMenu> findById_Role(String role);

    @Query(value = """
        SELECT DISTINCT rm.menu
        FROM HA_ROLE_MENU rm
        WHERE rm.role IN (:roles)
        """, nativeQuery = true)
    List<String> findDistinctMenusByRoles(@Param("roles") List<String> roles);
}