package com.banca_digital.dto;

import lombok.Data;

@Data
public class CreditoDTO {
    private String cuentaId;
    private double saldo;
    private String descripcion;
}
