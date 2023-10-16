package com.thamri.gestionstock.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.controllers.api.FournisseurApi;
import com.thamri.gestionstock.dto.FournisseurDto;
import com.thamri.gestionstock.services.FournisseurService;

@RestController
public class FournisseurController implements FournisseurApi {

	private FournisseurService fournisseurService;
	
	@Autowired
	public FournisseurController(FournisseurService fournisseurService) {
		
		this.fournisseurService=fournisseurService;
	}
	
	@Override
	public FournisseurDto save(FournisseurDto dto) {
		return fournisseurService.save(dto);
	}

	@Override
	public List<FournisseurDto> findAll() {
		
		return fournisseurService.findAll();
	}

	@Override
	public FournisseurDto findById(Integer id) {
		
		return fournisseurService.findById(id);
	}

	@Override
	public void delete(Integer id) {
		fournisseurService.delete(id);
		
	}

}
