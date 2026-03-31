package com.banca_digital;

import com.banca_digital.entidades.Cliente;
import com.banca_digital.entidades.CuentaActual;
import com.banca_digital.entidades.CuentaAhorro;
import com.banca_digital.entidades.OperacionCuenta;
import com.banca_digital.enums.EstadoCuenta;
import com.banca_digital.enums.TipoOperacion;
import com.banca_digital.repositorios.ClienteRepositorio;
import com.banca_digital.repositorios.CuentaBancariaRepositorio;
import com.banca_digital.repositorios.OperacionCuentaRepositorio;
import com.banca_digital.servicios.BancoServicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
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
	CommandLineRunner empezar(ClienteRepositorio clienteRepositorio,
							  CuentaBancariaRepositorio cuentaBancariaRepositorio,
							  OperacionCuentaRepositorio operacionCuentaBancariaRepositorio) {
		return args -> {
			Stream.of("Wilfredo", "Jose Ramon", "Julio", "Alan").forEach(nombre -> {
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre+"@gmail.com");
				clienteRepositorio.save(cliente);
			});

			// Le asignamos cuentas bancarias
			clienteRepositorio.findAll().forEach(cliente -> {
				CuentaActual cuentaActual = new CuentaActual();
				cuentaActual.setId(UUID.randomUUID().toString());
				cuentaActual.setSaldo(Math.random() * 90000);
				cuentaActual.setFechaCreacion(new Date());
				cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaActual.setCliente(cliente);
				cuentaActual.setSobregiro(9000);
				cuentaBancariaRepositorio.save(cuentaActual);

				CuentaAhorro cuentaAhorro = new CuentaAhorro();
				cuentaAhorro.setId(UUID.randomUUID().toString());
				cuentaAhorro.setSaldo(Math.random() * 90000);
				cuentaAhorro.setFechaCreacion(new Date());
				cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaAhorro.setCliente(cliente);
				cuentaAhorro.setTasaDeInteres(5.5);
				cuentaBancariaRepositorio.save(cuentaAhorro);
				});

			// Agregar las operaciones
			cuentaBancariaRepositorio.findAll().forEach(cuentaBancaria -> {
				for(int i=0;i<10;i++) {
					OperacionCuenta operacionCuenta = new OperacionCuenta();
					operacionCuenta.setFechaOperacion(new Date());
					operacionCuenta.setImporte(Math.random() * 12000);
					operacionCuenta.setTipoOperacion(Math.random() > 05 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);
					operacionCuenta.setCuentaBancaria(cuentaBancaria);
					operacionCuentaBancariaRepositorio.save(operacionCuenta);
				}
			});
		};
	}

}
