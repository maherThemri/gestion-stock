package com.thamri.gestionstock.services;

import java.util.List;

import com.thamri.gestionstock.dto.ArticleDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeFournisseurDto;
import com.thamri.gestionstock.dto.LigneVenteDto;

public interface ArticleService {
	
	ArticleDto save (ArticleDto dto);
	
	ArticleDto findById (Integer id);
	
	ArticleDto findByCodeArticle(String codeArticle);
	
	List<ArticleDto> findAll();
	
	List<LigneVenteDto> findHistorisqueVentes(Integer idArticle);
	
	List<LigneCommandeClientDto> findHistorisqueCommandeClient(Integer idArticle);
	
	List<LigneCommandeFournisseurDto> findHistorisqueCommandeFournisseur(Integer idArticle);
	
	List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);
	
	void delete (Integer id);

}
