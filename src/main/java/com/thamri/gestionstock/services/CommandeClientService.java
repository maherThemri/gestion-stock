package com.thamri.gestionstock.services;

import java.math.BigDecimal;
import java.util.List;

import com.thamri.gestionstock.dto.CommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.model.EtatCommande;

public interface CommandeClientService {

	public CommandeClientDto save(CommandeClientDto dto);
	
	public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);
	
	public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);
	
	public CommandeClientDto updateClient(Integer idCommande, Integer idClient);
	
	public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);
	
	public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

	public CommandeClientDto findById(Integer id);

	public CommandeClientDto findByCode(String code);

	public List<CommandeClientDto> findAll();
	
	public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);

	public void delete(Integer id);
}
