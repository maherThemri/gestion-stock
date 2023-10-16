package com.thamri.gestionstock.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.gestionstock.controllers.api.ArticleApi;
import com.thamri.gestionstock.dto.ArticleDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeFournisseurDto;
import com.thamri.gestionstock.dto.LigneVenteDto;
import com.thamri.gestionstock.services.ArticleService;

@RestController
public class ArticleController implements ArticleApi{
	
	private ArticleService articleService;
	
	@Autowired
	public ArticleController(ArticleService articleService) {
		
		this.articleService = articleService;
	}

	@Override
	public ArticleDto save(ArticleDto dto) {
		return articleService.save(dto);
	}

	@Override
	public ArticleDto findById(Integer id) {
		return articleService.findById(id); 
	}

	@Override
	public ArticleDto findByCodeArticle(String codeArticle) {
		return articleService.findByCodeArticle(codeArticle);
	}

	@Override
	public List<ArticleDto> findAll() {
		return articleService.findAll();
	}

	@Override
	public void delete(Integer id) {
		articleService.delete(id);
	}

	@Override
	public List<LigneVenteDto> findHistorisqueVentes(Integer idArticle) {
		
		return articleService.findHistorisqueVentes(idArticle);
	}

	@Override
	public List<LigneCommandeClientDto> findHistorisqueCommandeClient(Integer idArticle) {
		
		return articleService.findHistorisqueCommandeClient(idArticle);
	}

	@Override
	public List<LigneCommandeFournisseurDto> findHistorisqueCommandeFournisseur(Integer idArticle) {

		return articleService.findHistorisqueCommandeFournisseur(idArticle);
	}

	@Override
	public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
		
		return articleService.findAllArticleByIdCategory(idCategory);
	}

}
