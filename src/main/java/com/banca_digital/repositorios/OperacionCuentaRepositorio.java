package com.banca_digital.repositorios;

import com.banca_digital.entidades.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionCuentaRepositorio extends JpaRepository<OperacionCuenta, String> {
}
