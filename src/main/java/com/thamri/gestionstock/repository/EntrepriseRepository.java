package com.thamri.gestionstock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.gestionstock.model.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

}
