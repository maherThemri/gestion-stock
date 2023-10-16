package com.thamri.gestionstock.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.thamri.gestionstock.dto.ArticleDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeFournisseurDto;
import com.thamri.gestionstock.dto.LigneVenteDto;
import com.thamri.gestionstock.dto.VentesDto;
import com.thamri.gestionstock.exception.EntityNotFoundException;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidEntityException;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.model.Article;
import com.thamri.gestionstock.model.LigneCommandeClient;
import com.thamri.gestionstock.model.LigneCommandeFournisseur;
import com.thamri.gestionstock.model.LigneVente;
import com.thamri.gestionstock.repository.ArticleRepository;
import com.thamri.gestionstock.repository.LigneCommandeClientRepository;
import com.thamri.gestionstock.repository.LigneCommandeFournisseurRepository;
import com.thamri.gestionstock.repository.LigneVenteRepository;
import com.thamri.gestionstock.services.ArticleService;
import com.thamri.gestionstock.validator.ArticleValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

	private ArticleRepository articleRepository;
	private LigneVenteRepository venteRepository;
	private LigneCommandeClientRepository commandeClientRepository;
	private LigneCommandeFournisseurRepository commandeFournisseurRepository;

	public ArticleServiceImpl(ArticleRepository articleRepository,LigneVenteRepository venteRepository,
			LigneCommandeClientRepository commandeClientRepository,
			LigneCommandeFournisseurRepository commandeFournisseurRepository) {
		this.articleRepository = articleRepository;
		this.venteRepository = venteRepository;
		this.commandeClientRepository = commandeClientRepository;
		this.commandeFournisseurRepository = commandeFournisseurRepository;
	}

	@Override
	public ArticleDto save(ArticleDto dto) {
		List<String> errors = ArticleValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Article is not valide {}", dto);
			throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
		}
		return ArticleDto.fromEntity(articleRepository.save(ArticleDto.toEntity(dto)));
	}

	@Override
	public ArticleDto findById(Integer id) {
		if (id == null) {
			log.error("Article ID is null");
			return null;
		}
		Optional<Article> article = articleRepository.findById(id);

		return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() -> new EntityNotFoundException(
				"Aucun article avec l'ID = " + id + "n'été trouve dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND));
	}

	@Override
	public ArticleDto findByCodeArticle(String codeArticle) {
		if (!StringUtils.hasLength(codeArticle)) {
			log.error("Article CODE is null");
			return null;
		}
		Optional<Article> article = articleRepository.findArticleByCodeArticle(codeArticle);

		return Optional.of(ArticleDto.fromEntity(article.get())).orElseThrow(() -> new EntityNotFoundException(
				"Aucun article avec le Code  = " + codeArticle + "n'été trouve dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND));
	}

	@Override
	public List<ArticleDto> findAll() {
		
		return articleRepository.findAll().stream()
				.map(ArticleDto::fromEntity)
				.collect(Collectors.toList());
	}

	
	
	@Override
	public List<LigneVenteDto> findHistorisqueVentes(Integer idArticle) {
		return venteRepository.findAllByArticleId(idArticle).stream()
				.map(LigneVenteDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<LigneCommandeClientDto> findHistorisqueCommandeClient(Integer idArticle) {
		return commandeClientRepository.findAllByArticleId(idArticle).stream()
				.map(LigneCommandeClientDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<LigneCommandeFournisseurDto> findHistorisqueCommandeFournisseur(Integer idArticle) {
		return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
				.map(LigneCommandeFournisseurDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
		
		return articleRepository.findAllByCategoryId(idCategory).stream()
				.map(ArticleDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		if(id==null) {
			log.error("Article ID is null");
			return;
		}
		List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
	    if (!ligneCommandeClients.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
	    }
	    List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
	    if (!ligneCommandeFournisseurs.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
	          ErrorCodes.ARTICLE_ALREADY_IN_USE);
	    }
	    List<LigneVente> ligneVentes = venteRepository.findAllByArticleId(id);
	    if (!ligneVentes.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
	          ErrorCodes.ARTICLE_ALREADY_IN_USE);
	    }
	    articleRepository.deleteById(id);
	}

}
