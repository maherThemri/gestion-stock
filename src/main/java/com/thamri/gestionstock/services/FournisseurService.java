package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.FournisseurDto;

public interface FournisseurService {

	public FournisseurDto save (FournisseurDto dto);
	
	public List<FournisseurDto> findAll();
	
	public FournisseurDto findById(Integer id);
	
	public void delete(Integer id);
	
}
