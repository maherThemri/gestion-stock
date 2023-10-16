package com.thamri.gestionstock.controllers.api;
import static com.thamri.gestionstock.utils.Constants.APP_ROOT;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.thamri.gestionstock.dto.ClientDto;

//@RequestMapping(value=APP_ROOT+"/clients")
public interface ClientApi {

	@PostMapping(value= APP_ROOT+"/clients/create")
	public ClientDto save(@RequestBody ClientDto dto);

	@GetMapping(value= APP_ROOT+"/clients/{idClient}")
	public ClientDto findById(@PathVariable("idClient") Integer id);

	@GetMapping(value= APP_ROOT+"/clients/all")
	public List<ClientDto> findAll();

	@DeleteMapping(value =APP_ROOT+"/clients/delete/{idClient}" )
	public void delete(@PathVariable("idClient")Integer id);
}
