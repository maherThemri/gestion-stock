package com.thamri.gestionstock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thamri.gestionstock.model.Category;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class CategoryDto {
	
	private Integer id;

    private String code;

    private String designation;

    private Integer idEntreprise;

    @JsonIgnore
    private List<ArticleDto> articles;
    
    public static CategoryDto fromEntity (Category category) {
    	if(category == null) {
    		return null;
    		// TODO throw an exception
    	}
    	
    	return CategoryDto.builder()
    			.id(category.getId())
    			.code(category.getCode())
    			.idEntreprise(category.getIdEntreprise())
    			.designation(category.getDesignation())
    			.build();
    }
    
    public static Category toEntity(CategoryDto categoryDto) {
    	if(categoryDto == null) {
    		return null;
    		//TODO throw an exception
    	}
    	Category category= new Category();
    	category.setId(categoryDto.getId());
    	category.setCode(categoryDto.getCode());
    	category.setIdEntreprise(categoryDto.getIdEntreprise());
    	category.setDesignation(categoryDto.getDesignation());
    	
    	return category;
    }
    
}
