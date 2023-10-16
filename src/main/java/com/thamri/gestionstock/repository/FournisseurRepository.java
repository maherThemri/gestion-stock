package com.thamri.gestionstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.gestionstock.model.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {

}
