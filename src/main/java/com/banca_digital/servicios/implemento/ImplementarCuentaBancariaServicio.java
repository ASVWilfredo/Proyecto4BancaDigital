package com.banca_digital.servicios.implemento;

import com.banca_digital.dto.*;
import com.banca_digital.entidades.*;
import com.banca_digital.enums.TipoOperacion;
import com.banca_digital.excepciones.ExcepcionClienteNoEncontrado;
import com.banca_digital.excepciones.ExcepcionCuentaBancariaNoEncontrada;
import com.banca_digital.excepciones.ExcepcionSaldoInsuficiente;
import com.banca_digital.mappers.CuentaBancariaMapperImplemento;
import com.banca_digital.repositorios.ClienteRepositorio;
import com.banca_digital.repositorios.CuentaBancariaRepositorio;
import com.banca_digital.repositorios.OperacionCuentaRepositorio;
import com.banca_digital.servicios.CuentaBancariaServicio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ImplementarCuentaBancariaServicio implements CuentaBancariaServicio {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private CuentaBancariaRepositorio cuentaBancariaRepositorio;

    @Autowired
    private OperacionCuentaRepositorio operacionCuentaRepositorio;

    @Autowired
    private CuentaBancariaMapperImplemento cuentaBancariaMapper;

    @Override
    public ClienteDTO guardarCliente(ClienteDTO clienteDTO) {
        log.info("Guardando un nuevo cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBD = clienteRepositorio.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBD);
    }

    @Override
    public ClienteDTO getCliente(Long idCliente) throws ExcepcionClienteNoEncontrado {
        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new ExcepcionClienteNoEncontrado("Cliente no encontrado"));
        return cuentaBancariaMapper.mapearDeCliente(cliente);
    }

    @Override
    public ClienteDTO modificarCliente(ClienteDTO clienteDTO) {
        log.info("Modificando cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBD = clienteRepositorio.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBD);
    }

    @Override
    public List<ClienteDTO> buscarClientes(String keyword) {
        List<Cliente> clientes = clienteRepositorio.buscarClientes(keyword);
        List<ClienteDTO> clientesDTOS = clientes.stream().map(cliente ->
                cuentaBancariaMapper.mapearDeCliente(cliente)).collect(Collectors.toList());
        return clientesDTOS;
    }

    @Override
    public void eliminarCliente(Long clienteId) {
        clienteRepositorio.deleteById(clienteId);

    }

    @Override
    public CuentaActualDTO guardarCuentaBancariaActual(
            double saldoInicial, double sobregiro, Long clienteId) throws ExcepcionClienteNoEncontrado {
        Cliente cliente = clienteRepositorio.findById(clienteId).orElse(null);
        if(cliente == null){
            throw new ExcepcionClienteNoEncontrado("Cliente no encontrado");
        }
        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setSaldo(saldoInicial);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);
        CuentaActual cuentaActualBD = cuentaBancariaRepositorio.save(cuentaActual);
        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActualBD);
    }

    @Override
    public CuentaAhorroDTO guardarCuentaBancariaAhorro(
            double saldoInicial, double tasaInteres, Long clienteId) throws ExcepcionClienteNoEncontrado {
        Cliente cliente = clienteRepositorio.findById(clienteId).orElse(null);
        if(cliente == null){
            throw new ExcepcionClienteNoEncontrado("Cliente no encontrado");
        }
        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setSaldo(saldoInicial);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);
        CuentaAhorro cuentaAhorroBD = cuentaBancariaRepositorio.save(cuentaAhorro);
        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorroBD);
    }

    @Override
    public List<ClienteDTO> listaClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente)).collect(Collectors.toList());
        return clienteDTOS;
    }

    @Override
    public CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws ExcepcionCuentaBancariaNoEncontrada {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepositorio.findById(cuentaId).
                orElseThrow(() -> new ExcepcionCuentaBancariaNoEncontrada("Cuenta bancaria no encontrada"));
        if(cuentaBancaria instanceof CuentaAhorro){
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
        } else {
            CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual);
        }
    }

    @Override
    public void debito(String cuentaId, double saldo, String descripcion)
            throws ExcepcionCuentaBancariaNoEncontrada, ExcepcionSaldoInsuficiente {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepositorio.findById(cuentaId).
                orElseThrow(() -> new ExcepcionCuentaBancariaNoEncontrada("Cuenta bancaria no encontrada"));
        if(cuentaBancaria.getSaldo() < saldo){
            throw new ExcepcionSaldoInsuficiente("Saldo insuficiente");
        }
        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setImporte(saldo);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepositorio.save(operacionCuenta);
        cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() - saldo);
        cuentaBancariaRepositorio.save(cuentaBancaria);
    }

    @Override
    public void credito(String cuentaId, double saldo, String descripcion)
            throws ExcepcionCuentaBancariaNoEncontrada {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepositorio.findById(cuentaId).
                orElseThrow(() -> new ExcepcionCuentaBancariaNoEncontrada("Cuenta bancaria no encontrada"));
        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setImporte(saldo);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepositorio.save(operacionCuenta);
        cuentaBancaria.setSaldo(cuentaBancaria.getSaldo() + saldo);
        cuentaBancariaRepositorio.save(cuentaBancaria);
    }

    @Override
    public void transferir(String cuentaIdTitular, String cuentaIdDestinatario, double importe)
            throws ExcepcionCuentaBancariaNoEncontrada, ExcepcionSaldoInsuficiente {
        debito(cuentaIdTitular, importe, "Transferencia a : " + cuentaIdDestinatario);
        credito(cuentaIdDestinatario, importe, "Transferencia de : " + cuentaIdTitular);
    }

    @Override
    public List<CuentaBancariaDTO> listaCuentaBancarias() {
        List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepositorio.findAll();
        List<CuentaBancariaDTO> cuentaBancariasDTOS = cuentasBancarias.stream().map(cuentaBancaria -> {
            if(cuentaBancaria instanceof CuentaAhorro){
                CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
                return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
            } else {
                CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
                return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual);
            }
        }).collect(Collectors.toList());
        return cuentaBancariasDTOS;
    }

    @Override
    public List<OperacionCuentaDTO> historialOperaciones(String cuentaId) {
        List<OperacionCuenta> operacionesDeCuenta = operacionCuentaRepositorio.findByCuentaBancariaId(cuentaId);
        return operacionesDeCuenta.stream().map(operacionCuenta ->
            cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta)
        ).collect(Collectors.toList());
    }

    @Override
    public HistorialCuentaDTO getHistorialCuenta(String cuentaId, int pagina, int tamanio)
            throws ExcepcionCuentaBancariaNoEncontrada {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepositorio.findById(cuentaId).orElse(null);
        if(cuentaBancaria == null){
            throw new ExcepcionCuentaBancariaNoEncontrada("Cuenta bancaria no encontrada");
        }
        Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepositorio
                .findByCuentaBancariaIdOrderByFechaOperacionDesc(cuentaId, PageRequest.of(pagina, tamanio));
        HistorialCuentaDTO historialCuentaDTO = new HistorialCuentaDTO();
        List<OperacionCuentaDTO> operacionesCuentaDTOS = operacionesCuenta.
                getContent().stream().map(operacionCuenta -> cuentaBancariaMapper.
                        mapearDeOperacionCuenta(operacionCuenta)).collect(Collectors.toList());
        historialCuentaDTO.setOperacionCuentaDTOS(operacionesCuentaDTOS);
        historialCuentaDTO.setCuentaId(cuentaBancaria.getId());
        historialCuentaDTO.setSaldo(cuentaBancaria.getSaldo());
        historialCuentaDTO.setPaginaActual(pagina);
        historialCuentaDTO.setTamanioPagina(tamanio);
        historialCuentaDTO.setTotalPaginas(operacionesCuenta.getTotalPages());
        return historialCuentaDTO;
    }
}
