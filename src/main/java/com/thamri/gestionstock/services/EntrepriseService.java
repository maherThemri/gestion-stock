package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.EntrepriseDto;

public interface EntrepriseService {

	public EntrepriseDto save (EntrepriseDto dto);
	
	public List<EntrepriseDto> findAll();
	
	public EntrepriseDto findById(Integer id);
	
	public void delete(Integer id);
}
