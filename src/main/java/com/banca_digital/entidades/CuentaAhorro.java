package com.banca_digital.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("CAH") // Cuenta Ahorro
@NoArgsConstructor
@AllArgsConstructor
public class CuentaAhorro extends CuentaBancaria {
    private double tasaDeInteres;

}
