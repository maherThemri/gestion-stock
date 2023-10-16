package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.VentesDto;

public interface VentesService {
	
	public VentesDto save (VentesDto dto );
	
	public VentesDto findById (Integer id);
	
	public VentesDto findByCode (String code);
	
	public List<VentesDto> findAll ();
	
	public void delete(Integer id);
	

}
