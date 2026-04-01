package com.banca_digital.web;

import com.banca_digital.dto.*;
import com.banca_digital.excepciones.ExcepcionCuentaBancariaNoEncontrada;
import com.banca_digital.excepciones.ExcepcionSaldoInsuficiente;
import com.banca_digital.servicios.CuentaBancariaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaControlador {
    @Autowired
    private CuentaBancariaServicio cuentaBancariaServicio;

    @GetMapping("/cuentas/{cuentaId}")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBancaria(@PathVariable String cuentaId)
            throws ExcepcionCuentaBancariaNoEncontrada {
        return cuentaBancariaServicio.getCuentaBancaria(cuentaId);
    }

    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias() {
        return cuentaBancariaServicio.listaCuentaBancarias();
    }

    @GetMapping("/cuentas/{cuentaId}/operaciones")
    public List<OperacionCuentaDTO> historialOperaciones(@PathVariable String cuentaId) {
        return cuentaBancariaServicio.historialOperaciones(cuentaId);
    }

    @GetMapping("/cuentas/{cuentaId}/paginaOperaciones")
    public HistorialCuentaDTO historialOperacionesPaginado(
            @PathVariable String cuentaId, @RequestParam(name="pagina", defaultValue = "0") int pagina,
            @RequestParam(name="tamanio", defaultValue = "5") int tamanio) throws ExcepcionCuentaBancariaNoEncontrada {
        return cuentaBancariaServicio.getHistorialCuenta(cuentaId, pagina, tamanio);
    }

    @PostMapping("cuentas/debito")
    public DebitoDTO realizarDebito(@RequestBody DebitoDTO debitoDTO)
            throws ExcepcionSaldoInsuficiente, ExcepcionCuentaBancariaNoEncontrada {
        cuentaBancariaServicio.debito(debitoDTO.getCuentaId(), debitoDTO.getSaldo(), debitoDTO.getDescripcion());
        return  debitoDTO;
    }

    @PostMapping("cuentas/credito")
    public CreditoDTO realizarCredito(@RequestBody CreditoDTO creditoDTO)
            throws ExcepcionCuentaBancariaNoEncontrada {
        cuentaBancariaServicio.credito(creditoDTO.getCuentaId(), creditoDTO.getSaldo(), creditoDTO.getDescripcion());
        return creditoDTO;
    }

    @PostMapping("/cuentas/transferencia")
    public void realizarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO)
            throws ExcepcionSaldoInsuficiente, ExcepcionCuentaBancariaNoEncontrada {
        cuentaBancariaServicio.transferir(transferenciaDTO.getCuentaTitular(),
                transferenciaDTO.getCuentaDestinatario(), transferenciaDTO.getImporte());
    }
}
