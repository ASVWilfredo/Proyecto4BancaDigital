package com.banca_digital.dto;

import lombok.Data;

@Data
public class TransferenciaDTO {
    private String cuentaTitular;
    private String cuentaDestinatario;
    private double importe;
    private String descripcion;


}
