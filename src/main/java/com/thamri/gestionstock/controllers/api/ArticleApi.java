package com.thamri.gestionstock.controllers.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.thamri.gestionstock.dto.ArticleDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeFournisseurDto;
import com.thamri.gestionstock.dto.LigneVenteDto;

import static com.thamri.gestionstock.utils.Constants.APP_ROOT;

public interface ArticleApi {

	@PostMapping(value=APP_ROOT+"/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	ArticleDto save(@RequestBody ArticleDto dto);

	@GetMapping(value= APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
	ArticleDto findById(@PathVariable("idArticle") Integer id);
	
	@GetMapping(value= APP_ROOT + "/articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
	ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

	@GetMapping(value= APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ArticleDto> findAll();
	
	@GetMapping(value= APP_ROOT + "/articles/historique/vente/{idArticle}")
	List<LigneVenteDto> findHistorisqueVentes(@PathVariable Integer idArticle);
	
	@GetMapping(value= APP_ROOT + "/articles/historique/commandeclient/{idArticle}")
	List<LigneCommandeClientDto> findHistorisqueCommandeClient(@PathVariable Integer idArticle);
	
	@GetMapping(value= APP_ROOT + "/articles/historique/commandefournisseur/{idArticle}")
	List<LigneCommandeFournisseurDto> findHistorisqueCommandeFournisseur(@PathVariable Integer idArticle);
	
	@GetMapping(value= APP_ROOT + "/articles/filter/category/{idCategory}")
	List<ArticleDto> findAllArticleByIdCategory(@PathVariable Integer idCategory);

	@DeleteMapping(value= APP_ROOT + "/articles/delete/{idArticle}")
	void delete(@PathVariable("idArticle") Integer id);
}
