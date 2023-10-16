package com.thamri.gestionstock.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.controllers.api.ClientApi;
import com.thamri.gestionstock.dto.ClientDto;
import com.thamri.gestionstock.services.ClientService;

@RestController
public class ClientController implements ClientApi {

	private ClientService clientService;
	
	@Autowired
	  public ClientController(ClientService clientService) {
	    this.clientService = clientService;
	  }
	
	@Override
	public ClientDto save(ClientDto dto) {
		
		return clientService.save(dto);
	}

	@Override
	public ClientDto findById(Integer id) {
		
		return clientService.findById(id);
	}

	@Override
	public List<ClientDto> findAll() {
		
		return clientService.findAll();
	}

	@Override
	public void delete(Integer id) {
		
		clientService.delete(id);
		
	}
 
}
