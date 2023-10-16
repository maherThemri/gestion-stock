package com.thamri.gestionstock.services.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.thamri.gestionstock.dto.ArticleDto;
import com.thamri.gestionstock.dto.LigneVenteDto;
import com.thamri.gestionstock.dto.MvtStkDto;
import com.thamri.gestionstock.dto.VentesDto;
import com.thamri.gestionstock.exception.EntityNotFoundException;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidEntityException;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.model.Article;
import com.thamri.gestionstock.model.LigneVente;
import com.thamri.gestionstock.model.SourceMvtStk;
import com.thamri.gestionstock.model.TypeMvtStk;
import com.thamri.gestionstock.model.Ventes;
import com.thamri.gestionstock.repository.ArticleRepository;
import com.thamri.gestionstock.repository.LigneVenteRepository;
import com.thamri.gestionstock.repository.VentesRepository;
import com.thamri.gestionstock.services.MvtStkService;
import com.thamri.gestionstock.services.VentesService;
import com.thamri.gestionstock.validator.VentesValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

	private VentesRepository ventesRepository;
	private ArticleRepository articleRepository;
	private LigneVenteRepository ligneVenteRepository;
	private MvtStkService mvtStkService;

	@Autowired
	public VentesServiceImpl(VentesRepository ventesRepository, ArticleRepository articleRepository,
			LigneVenteRepository ligneVenteRepository, MvtStkService mvtStkService) {
		this.ventesRepository = ventesRepository;
		this.articleRepository = articleRepository;
		this.ligneVenteRepository = ligneVenteRepository;
		this.mvtStkService = mvtStkService;
	}

	@Override
	public VentesDto save(VentesDto dto) {
		List<String> errors = VentesValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Ventes n'est pas valide");
			throw new InvalidEntityException("L'objet ventes n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
		}

		List<String> articleErrors = new ArrayList<>();

		dto.getLigneVente().forEach(ligneVenteDto -> {
			Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
			if (article.isEmpty()) {
				articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId()
						+ " n'a ete trouve dans la BDD");
			}
		});

		if (!articleErrors.isEmpty()) {
			log.error("One or more articles were not found in the DB {}", errors);
			throw new InvalidEntityException("Un ou plusieurs articles n'ont trouve dans la BDD",
					ErrorCodes.VENTE_NOT_VALID, errors);
		}

		Ventes savedVentes = ventesRepository.save(VentesDto.toEntity(dto));

		dto.getLigneVente().forEach(ligneVenteDto -> {
			LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
			ligneVente.setVente(savedVentes);
			ligneVenteRepository.save(ligneVente);
			updateMvtStk(ligneVente);
		});
		return VentesDto.fromEntity(savedVentes);
	}

	@Override
	public VentesDto findById(Integer id) {
		if (id == null) {
			log.error("Vente ID is null");
			return null;
		}
		return ventesRepository.findById(id).map(VentesDto::fromEntity).orElseThrow(
				() -> new EntityNotFoundException("Aucun vente n'a été touve dans la BDD", ErrorCodes.VENTE_NOT_FOUND));
	}

	@Override
	public VentesDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			log.error("Vente CODE is null");
			return null;
		}
		return ventesRepository.findVenteByCode(code).map(VentesDto::fromEntity).orElseThrow(
				() -> new EntityNotFoundException("Aucun vente n'a été touve dans la BDD", ErrorCodes.VENTE_NOT_VALID));
	}

	@Override
	public List<VentesDto> findAll() {
		
		return ventesRepository.findAll().stream().map(VentesDto::fromEntity).collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		if(id == null) {
			log.error("Vente ID is NULL");
			return;
		}
		List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
	    if (!ligneVentes.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer une vente ...",
	          ErrorCodes.VENTE_ALREADY_IN_USE);
	    }
		ventesRepository.deleteById(id);

	}
	
	private void updateMvtStk(LigneVente lig) {
	    MvtStkDto mvtStkDto = MvtStkDto.builder()
	        .article(ArticleDto.fromEntity(lig.getArticle()))
	        .dateMvt(Instant.now())
	        .typeMvt(TypeMvtStk.SORTIE)
	        .sourceMvt(SourceMvtStk.VENTE)
	        .quantite(lig.getQuantite())
	        .idEntreprise(lig.getIdEntreprise())
	        .build();
	    mvtStkService.sortieStock(mvtStkDto);
	  }

}
