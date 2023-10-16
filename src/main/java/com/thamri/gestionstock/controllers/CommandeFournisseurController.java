package com.thamri.gestionstock.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.controllers.api.CommandeFournisseurApi;
import com.thamri.gestionstock.dto.CommandeFournisseurDto;
import com.thamri.gestionstock.model.EtatCommande;
import com.thamri.gestionstock.services.CommandeFournisseurService;

@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

	private CommandeFournisseurService commandeFournisseurService;
	
	@Autowired
	public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
		this.commandeFournisseurService=commandeFournisseurService;
	}
	
	@Override
	public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
		
		return commandeFournisseurService.save(dto);
	}

	@Override
	public CommandeFournisseurDto findById(Integer id) {
		
		return commandeFournisseurService.findById(id);
	}

	@Override
	public CommandeFournisseurDto findByCode(String code) {
		
		return commandeFournisseurService.findByCode(code);
	}

	@Override
	public List<CommandeFournisseurDto> findAll() {
		
		return commandeFournisseurService.findAll();
	}

	@Override
	public void delete(Integer id) {
		commandeFournisseurService.delete(id);
		
	}

	@Override
	public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
	    return commandeFournisseurService.updateEtatCommande(idCommande, etatCommande);
	}

	@Override
	public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande,
			BigDecimal quantite) {
	    return commandeFournisseurService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
	}

	@Override
	public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
	    return commandeFournisseurService.updateFournisseur(idCommande, idFournisseur);
	}

	@Override
	public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
	    return commandeFournisseurService.updateArticle(idCommande, idLigneCommande, idArticle);
	}

	@Override
	public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
	    return commandeFournisseurService.deleteArticle(idCommande, idLigneCommande);
	}

}
