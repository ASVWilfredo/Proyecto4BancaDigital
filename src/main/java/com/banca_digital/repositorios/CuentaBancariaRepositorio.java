package com.banca_digital.repositorios;

import com.banca_digital.entidades.CuentaBancaria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CuentaBancariaRepositorio extends JpaRepository<CuentaBancaria, String> {

}
