package com.thamri.gestionstock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.gestionstock.model.Ventes;

public interface VentesRepository extends JpaRepository<Ventes, Integer>{
	
	Optional<Ventes> findVenteByCode(String code);

}
