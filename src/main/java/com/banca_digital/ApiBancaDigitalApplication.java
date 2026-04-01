package com.banca_digital;

import com.banca_digital.dto.ClienteDTO;
import com.banca_digital.dto.CuentaActualDTO;
import com.banca_digital.dto.CuentaAhorroDTO;
import com.banca_digital.dto.CuentaBancariaDTO;
import com.banca_digital.entidades.*;
import com.banca_digital.servicios.BancoServicio;
import com.banca_digital.servicios.CuentaBancariaServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BancoServicio bancoServicio) {
		return args -> {
			bancoServicio.consultar();
		};
	}

	//@Bean
	CommandLineRunner empezar(CuentaBancariaServicio  cuentaBancariaServicio) {
		return args -> {
			Stream.of("Wilfredo", "Jose Ramon", "Julio", "Alan").forEach(nombre -> {
				ClienteDTO cliente = new ClienteDTO();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre+"@gmail.com");
				cuentaBancariaServicio.guardarCliente(cliente);
			});
			cuentaBancariaServicio.listaClientes().forEach(cliente -> {
				try {
					cuentaBancariaServicio.guardarCuentaBancariaActual(Math.random() * 90000, 9000, cliente.getId());
					cuentaBancariaServicio.guardarCuentaBancariaAhorro(120000, 5.5, cliente.getId());
					List<CuentaBancariaDTO> cuentaBancarias = cuentaBancariaServicio.listaCuentaBancarias();
					for(CuentaBancariaDTO cuentaBancaria : cuentaBancarias) {
						for(int i=0;i<10;i++) {
							String cuentaId;
							if (cuentaBancaria instanceof CuentaAhorroDTO) {
								cuentaId = ((CuentaAhorroDTO) cuentaBancaria).getId();
							} else if (cuentaBancaria instanceof CuentaActualDTO) {
								cuentaId = ((CuentaActualDTO) cuentaBancaria).getId();
							} else {
								throw new IllegalStateException("Tipo de cuenta desconocido: " + cuentaBancaria.getClass().getName());
							}
							cuentaBancariaServicio.credito(cuentaId,10000 +
									Math.random()*120000, "Credito");
							cuentaBancariaServicio.debito(cuentaId,1000 +
									Math.random()*9000, "Debito");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		};
	}

}
