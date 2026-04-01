package com.banca_digital.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OperacionCuentaDTO {
    private Long id;
    private Date fechaOperacion;
    private double saldo;
    private String tipoOperacion;
    private String descripcion;
}
