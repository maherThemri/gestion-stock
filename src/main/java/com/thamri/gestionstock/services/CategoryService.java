package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.CategoryDto;

public interface CategoryService {
	
	public CategoryDto save (CategoryDto dto);
	
	public List<CategoryDto> findAll ();
	
	public CategoryDto findById (Integer id);
	
	public CategoryDto findByCode(String code);
	
	public void delete(Integer id);

}
