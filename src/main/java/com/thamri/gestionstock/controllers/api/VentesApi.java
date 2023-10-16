package com.thamri.gestionstock.controllers.api;

import static com.thamri.gestionstock.utils.Constants.VENTES_ENDPOINT;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thamri.gestionstock.dto.VentesDto;

public interface VentesApi {
	
	@PostMapping(VENTES_ENDPOINT + "/create")
	public VentesDto save(@RequestBody VentesDto dto);

	@GetMapping(VENTES_ENDPOINT + "/{idVente}")
	public VentesDto findById(@PathVariable("idVente") Integer id);

	@GetMapping(VENTES_ENDPOINT + "/{codeVente}")
	public VentesDto findByCode(@PathVariable("codeVente") String code);

	@GetMapping(VENTES_ENDPOINT + "/all")
	public List<VentesDto> findAll();

	@DeleteMapping(VENTES_ENDPOINT + "/delete/{idVente}")
	public void delete(Integer id);
}
