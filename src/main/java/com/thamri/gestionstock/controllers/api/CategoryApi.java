package com.thamri.gestionstock.controllers.api;

import static com.thamri.gestionstock.utils.Constants.APP_ROOT;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thamri.gestionstock.dto.CategoryDto;

public interface CategoryApi {

	@PostMapping(value = APP_ROOT + "/categories/create")
	public CategoryDto save(@RequestBody CategoryDto dto);

	@GetMapping(value = APP_ROOT + "/categories/all")
	public List<CategoryDto> findAll();

	@GetMapping(value = APP_ROOT + "/categories/{idCategory}")
	public CategoryDto findById(@PathVariable("idCategory") Integer id);

	@GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}")
	public CategoryDto findByCode(@PathVariable("codeCategory") String code);

	@DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
	public void delete(@PathVariable("idCategory") Integer id);
}
