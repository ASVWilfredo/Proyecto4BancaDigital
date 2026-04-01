package com.banca_digital.dto;

import com.banca_digital.enums.EstadoCuenta;
import lombok.Data;

import java.util.Date;

@Data
public class CuentaActualDTO extends CuentaBancariaDTO {
    private String id;
    private double saldo;
    private Date fechaCreacion;
    private EstadoCuenta estadoCuenta;
    private ClienteDTO clienteDTO;
    private double sobregiro;
    
}
