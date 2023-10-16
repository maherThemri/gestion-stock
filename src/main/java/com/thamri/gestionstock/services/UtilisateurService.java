package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.ChangerMotDePasseUtilisateurDto;
import com.thamri.gestionstock.dto.UtilisateurDto;

public interface UtilisateurService {

	UtilisateurDto save(UtilisateurDto dto);

	UtilisateurDto findById(Integer id);

	List<UtilisateurDto> findAll();

	void delete(Integer id);

	UtilisateurDto findByEmail(String email);

	UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);

}
