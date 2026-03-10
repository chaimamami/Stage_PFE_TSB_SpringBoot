package com.banque.cheques.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RfMenuId implements Serializable {
    private String menu;
    private String codLan;
}