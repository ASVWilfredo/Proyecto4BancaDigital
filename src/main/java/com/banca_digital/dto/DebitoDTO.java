package com.banca_digital.dto;

import lombok.Data;

@Data
public class DebitoDTO {
    private String cuentaId;
    private double saldo;
    private String descripcion;
}
