package com.banca_digital.web;

import com.banca_digital.dto.ClienteDTO;
import com.banca_digital.excepciones.ExcepcionClienteNoEncontrado;
import com.banca_digital.servicios.CuentaBancariaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteControlador {
    @Autowired
    private CuentaBancariaServicio cuentaBancariaServicio;

    @GetMapping("/clientes")
    public List<ClienteDTO> listarClientes(){
        return cuentaBancariaServicio.listaClientes();
    }

    @GetMapping("/clientes/{id}")
    public ClienteDTO listarDatosCliente(
            @PathVariable(name = "id") Long clienteId) throws ExcepcionClienteNoEncontrado {
        return cuentaBancariaServicio.getCliente(clienteId);
    }

    @PostMapping("/clientes")
    public ClienteDTO guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        return cuentaBancariaServicio.guardarCliente(clienteDTO);
    }

    @PutMapping("/clientes/{clienteId}")
    public ClienteDTO modificarCliente(@PathVariable Long clienteId, @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(clienteId);
        return cuentaBancariaServicio.modificarCliente(clienteDTO);
    }

    @DeleteMapping("/clientes/{id}")
    public void eliminarCliente(@PathVariable(name = "id") Long clienteId) {
        cuentaBancariaServicio.eliminarCliente(clienteId);
    }
}
