package com.thamri.gestionstock.controllers.api;

import static com.thamri.gestionstock.utils.Constants.APP_ROOT;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thamri.gestionstock.dto.CommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.model.EtatCommande;

public interface CommandeClientApi {
	
	@PostMapping(APP_ROOT + "/commandesclients/create")
	public ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

	@PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
	public ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable Integer idCommande, @PathVariable EtatCommande etatCommande);
	
	@PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")	
	public ResponseEntity<CommandeClientDto> updateQuantiteCommande(
			@PathVariable Integer idCommande, @PathVariable Integer idLigneCommande, @PathVariable BigDecimal quantite);
	
	@PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")	
	public ResponseEntity<CommandeClientDto> updateClient(@PathVariable Integer idCommande, @PathVariable Integer idClient);
	
	@PatchMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
	public ResponseEntity<CommandeClientDto> updateArticle(
			@PathVariable Integer idCommande, @PathVariable Integer idLigneCommande, @PathVariable Integer idArticle);
	
	@DeleteMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}")
	public ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable Integer idCommande, @PathVariable Integer idLigneCommande);

	@GetMapping(APP_ROOT + "/commandesclients/{idCommandeClient}")
	public ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

	@GetMapping(APP_ROOT + "/commandesclients/filter/{codeCommandeClient}")
	public ResponseEntity<CommandeClientDto> findByCode(@PathVariable String code);

	@GetMapping(APP_ROOT + "/commandesclients/all")
	public ResponseEntity<List<CommandeClientDto>> findAll();
	
	 @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
	  ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

	@DeleteMapping(APP_ROOT + "/commandesclients/delete/{idCommandeClient}")
	 ResponseEntity<Void> delete (@PathVariable("idCommandeClient") Integer id);

}
