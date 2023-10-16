package com.thamri.gestionstock.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.controllers.api.CategoryApi;
import com.thamri.gestionstock.dto.CategoryDto;
import com.thamri.gestionstock.services.CategoryService;

@RestController
public class CategoryController implements CategoryApi{

	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService =categoryService;
	}
	
	@Override
	public CategoryDto save(CategoryDto dto) {
		return categoryService.save(dto);
	}

	@Override
	public List<CategoryDto> findAll() {
		return categoryService.findAll();
	}

	@Override
	public CategoryDto findById(Integer id) {
		return categoryService.findById(id);
	}

	@Override
	public CategoryDto findByCode(String code) {
		return categoryService.findByCode(code);
	}

	@Override
	public void delete(Integer id) {
		categoryService.delete(id);
	}

}
