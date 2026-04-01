package com.banca_digital.servicios;

import com.banca_digital.dto.ClienteDTO;
import com.banca_digital.dto.CuentaActualDTO;
import com.banca_digital.dto.CuentaAhorroDTO;
import com.banca_digital.dto.CuentaBancariaDTO;
import com.banca_digital.entidades.Cliente;
import com.banca_digital.entidades.CuentaActual;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.CuentaBancaria;
import com.banca_digital.excepciones.ExcepcionClienteNoEncontrado;
import com.banca_digital.excepciones.ExcepcionCuentaBancariaNoEncontrada;
import com.banca_digital.excepciones.ExcepcionSaldoInsuficiente;

import java.util.List;

public interface CuentaBancariaServicio {
    ClienteDTO guardarCliente(ClienteDTO clienteDTO);

    ClienteDTO getCliente(Long clienteId) throws ExcepcionClienteNoEncontrado;

    ClienteDTO modificarCliente(ClienteDTO clienteDTO);

    void eliminarCliente(Long clienteId);

    CuentaActualDTO guardarCuentaBancariaActual(
            double saldoInicial, double sobregiro, Long clienteId) throws ExcepcionClienteNoEncontrado;

    CuentaAhorroDTO guardarCuentaBancariaAhorro(
            double saldoInicial, double tasaInteres, Long clienteId) throws ExcepcionClienteNoEncontrado;

    List<ClienteDTO> listaClientes();

    CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws ExcepcionCuentaBancariaNoEncontrada;

    void debito(String cuentaId, double saldo, String descripcion)
            throws ExcepcionCuentaBancariaNoEncontrada, ExcepcionSaldoInsuficiente;

    void credito(String cuentaId, double saldo, String descripcion) throws ExcepcionCuentaBancariaNoEncontrada;

    void transferir(String cuentaIdTitular, String cuentaIdDestinatario, double importe)
            throws ExcepcionCuentaBancariaNoEncontrada, ExcepcionSaldoInsuficiente;

    List<CuentaBancariaDTO> listaCuentaBancarias();
}
