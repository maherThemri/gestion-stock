package com.thamri.gestionstock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.gestionstock.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	Optional<Category> findCategoryByCode(String code);
	

}
