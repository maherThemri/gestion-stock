package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.ClientDto;

public interface ClientService {
	
	public ClientDto save (ClientDto dto);
	
	public ClientDto findById(Integer id);
	
	public List<ClientDto> findAll();
	
	public void delete(Integer id);

}
