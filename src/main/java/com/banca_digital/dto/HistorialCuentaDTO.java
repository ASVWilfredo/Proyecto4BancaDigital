package com.banca_digital.dto;

import lombok.Data;

import java.util.List;

@Data
public class HistorialCuentaDTO {
    private String cuentaId;
    private double saldo;
    private int paginaActual;
    private int totalPaginas;
    private int tamanioPagina;
    private List<OperacionCuentaDTO> operacionCuentaDTOS;
}
