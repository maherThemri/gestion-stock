package com.thamri.gestionstock.controllers.api;

import static com.thamri.gestionstock.utils.Constants.FOURNISSEUR_ENDPOINT;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thamri.gestionstock.dto.FournisseurDto;

public interface FournisseurApi {

	@PostMapping(FOURNISSEUR_ENDPOINT+"/create")
	public FournisseurDto save(@RequestBody FournisseurDto dto);

	@GetMapping(FOURNISSEUR_ENDPOINT+"/all")
	public List<FournisseurDto> findAll();

	@GetMapping(FOURNISSEUR_ENDPOINT+"/{idFournisseur}")
	public FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

	@DeleteMapping(FOURNISSEUR_ENDPOINT+"/delete/{idFournisseur}")
	public void delete(@PathVariable("idFournisseur") Integer id);
}
