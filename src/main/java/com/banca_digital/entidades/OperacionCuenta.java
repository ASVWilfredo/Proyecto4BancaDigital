package com.banca_digital.entidades;

import com.banca_digital.enums.TipoOperacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacionCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaOperacion;

    private double importe;

    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;

    @ManyToOne
    private CuentaBancaria cuentaBancaria;

    private String descripcion;
}
