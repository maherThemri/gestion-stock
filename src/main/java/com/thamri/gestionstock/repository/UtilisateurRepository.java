package com.thamri.gestionstock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thamri.gestionstock.model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
	
	Optional<Utilisateur> findUtilisateurByEmail(String email);
}
