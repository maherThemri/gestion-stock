package com.thamri.gestionstock.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.controllers.api.UtilisateurApi;
import com.thamri.gestionstock.dto.ChangerMotDePasseUtilisateurDto;
import com.thamri.gestionstock.dto.UtilisateurDto;
import com.thamri.gestionstock.services.UtilisateurService;

@RestController
public class UtilisateurController implements UtilisateurApi {

  private UtilisateurService utilisateurService;

  @Autowired
  public UtilisateurController(UtilisateurService utilisateurService) {
    this.utilisateurService = utilisateurService;
  }

  @Override
  public UtilisateurDto save(UtilisateurDto dto) {
    return utilisateurService.save(dto);
  }

  @Override
  public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
    return utilisateurService.changerMotDePasse(dto);
  }

  @Override
  public UtilisateurDto findById(Integer id) {
    return utilisateurService.findById(id);
  }

  @Override
  public UtilisateurDto findByEmail(String email) {
    return utilisateurService.findByEmail(email);
  }

  @Override
  public List<UtilisateurDto> findAll() {
    return utilisateurService.findAll();
  }

  @Override
  public void delete(Integer id) {
    utilisateurService.delete(id);
  }
}
