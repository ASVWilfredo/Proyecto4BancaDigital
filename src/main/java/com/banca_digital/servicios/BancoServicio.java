package com.banca_digital.servicios;

import com.banca_digital.entidades.CuentaActual;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.CuentaBancaria;
import com.banca_digital.repositorios.CuentaBancariaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BancoServicio {
    @Autowired
    private CuentaBancariaRepositorio cuentaBancariaRepositorio;

    public void consultar() {
        CuentaBancaria cuentaBancariaBD = cuentaBancariaRepositorio.
                findById("2d5694e4-2d76-4e37-b842-2cbbe91d3d75").orElse(null);
        if(cuentaBancariaBD != null) {
            System.out.println("********************************");
            System.out.println("ID: " + cuentaBancariaBD.getId());
            System.out.println("Saldo de la cuenta: " + cuentaBancariaBD.getSaldo());
            System.out.println("Estado: " + cuentaBancariaBD.getEstadoCuenta());
            System.out.println("Fecha de creacion : " + cuentaBancariaBD.getFechaCreacion());
            System.out.println("Cliente: " + cuentaBancariaBD.getCliente().getNombre());
            System.out.println("Nombre de la clase : " + cuentaBancariaBD.getClass().getSimpleName());

            if(cuentaBancariaBD instanceof CuentaActual) {
                System.out.println("Sobregiro : " + ((CuentaActual)cuentaBancariaBD).getSobregiro());
            } else if(cuentaBancariaBD instanceof CuentaAhorro) {
                System.out.println("Tase de interes : " + ((CuentaAhorro) cuentaBancariaBD).getTasaDeInteres());
            }

            cuentaBancariaBD.getOperacionesCuenta().forEach(operacion -> {
                System.out.println("--------------------------------------------------");
                System.out.println("Tipo de operacion: " + operacion.getTipoOperacion());
                System.out.println("Fecha de operacion : " + operacion.getFechaOperacion());
                System.out.println("Importe : " + operacion.getImporte());
            });
        }
    }

}
