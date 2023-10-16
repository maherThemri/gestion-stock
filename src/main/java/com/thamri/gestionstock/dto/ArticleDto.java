package com.thamri.gestionstock.dto;

import java.math.BigDecimal;

import com.thamri.gestionstock.model.Article;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class ArticleDto {
	
	private Integer id;

	private String codeArticle;

	private String designation;

	private BigDecimal prixUnitaireHt;

	private BigDecimal tauxTva;

	private BigDecimal prixUnitaireTtc;

	private String photo;

	private CategoryDto category;
	
	private Integer idEntreprise;
	
	public static ArticleDto fromEntity (Article article) {
		
		if(article == null) {
			return null;
			//TODO throw an exception
		}
		
		return ArticleDto.builder()
				.id(article.getId())
				.codeArticle(article.getCodeArticle())
				.designation(article.getDesignation())
				.prixUnitaireHt(article.getPrixUnitaireHt())
				.tauxTva(article.getTauxTva())
				.prixUnitaireTtc(article.getPrixUnitaireTtc())
				.photo(article.getPhoto())
				.idEntreprise(article.getIdEntreprise())
				.category(CategoryDto.fromEntity(article.getCategory()))
				.build();
	}
	
	public static Article toEntity (ArticleDto articleDto) {
		
		if(articleDto == null) {
			return null;
			//TODO throw an exception
		}
		
		Article article = new Article();
		article.setId(articleDto.getId());
		article.setCodeArticle(articleDto.getCodeArticle());
		article.setDesignation(articleDto.getDesignation());
		article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
		article.setTauxTva(articleDto.getTauxTva());
		article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
		article.setPhoto(articleDto.getPhoto());
		article.setIdEntreprise(articleDto.getIdEntreprise());
		article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
		
		return article;
	}
}
