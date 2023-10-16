package com.thamri.gestionstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.gestionstock.model.CommandeFournisseur;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, Integer> {
	
	Optional<CommandeFournisseur>  findCommandeFournisseurByCode(String code);

	List<CommandeFournisseur> findAllByFournisseurId(Integer id);

}
