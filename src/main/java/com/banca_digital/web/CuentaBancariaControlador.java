package com.banca_digital.web;

import com.banca_digital.dto.CuentaBancariaDTO;
import com.banca_digital.excepciones.ExcepcionCuentaBancariaNoEncontrada;
import com.banca_digital.servicios.CuentaBancariaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaControlador {
    @Autowired
    private CuentaBancariaServicio cuentaBancariaServicio;

    @GetMapping("/cuentas/{cuentaId}")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBancaria(@PathVariable String cuentaId) throws ExcepcionCuentaBancariaNoEncontrada {
        return cuentaBancariaServicio.getCuentaBancaria(cuentaId);
    }

    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias() {
        return cuentaBancariaServicio.listaCuentaBancarias();
    }
}
