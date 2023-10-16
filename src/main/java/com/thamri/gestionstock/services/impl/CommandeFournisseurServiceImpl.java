package com.thamri.gestionstock.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.thamri.gestionstock.dto.ArticleDto;
import com.thamri.gestionstock.dto.CommandeFournisseurDto;
import com.thamri.gestionstock.dto.FournisseurDto;
import com.thamri.gestionstock.dto.LigneCommandeFournisseurDto;
import com.thamri.gestionstock.dto.MvtStkDto;
import com.thamri.gestionstock.exception.EntityNotFoundException;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidEntityException;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.model.Article;
import com.thamri.gestionstock.model.CommandeFournisseur;
import com.thamri.gestionstock.model.EtatCommande;
import com.thamri.gestionstock.model.Fournisseur;
import com.thamri.gestionstock.model.LigneCommandeFournisseur;
import com.thamri.gestionstock.model.SourceMvtStk;
import com.thamri.gestionstock.model.TypeMvtStk;
import com.thamri.gestionstock.repository.ArticleRepository;
import com.thamri.gestionstock.repository.CommandeFournisseurRepository;
import com.thamri.gestionstock.repository.FournisseurRepository;
import com.thamri.gestionstock.repository.LigneCommandeFournisseurRepository;
import com.thamri.gestionstock.services.CommandeFournisseurService;
import com.thamri.gestionstock.services.MvtStkService;
import com.thamri.gestionstock.validator.ArticleValidator;
import com.thamri.gestionstock.validator.CommandeFournisseurValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

	private CommandeFournisseurRepository commandeFournisseurRepository;
	private FournisseurRepository fournisseurRepository;
	private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
	private ArticleRepository articleRepository;
	private MvtStkService mvtStkService;

	@Autowired
	public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
			FournisseurRepository fournisseurRepository,
			LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
			ArticleRepository articleRepository, MvtStkService mvtStkService) {

		this.commandeFournisseurRepository = commandeFournisseurRepository;
		this.fournisseurRepository = fournisseurRepository;
		this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
		this.articleRepository = articleRepository;
		this.mvtStkService = mvtStkService;
	}

	@Override
	public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
		List<String> errors = CommandeFournisseurValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Commande fournisseur is not valide {}", dto);
			throw new InvalidEntityException("Commande fournisseur n'est pas valide",
					ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
		}

		Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
		if (fournisseur.isEmpty()) {
			log.warn("Fournisseur with Id {} was not found in the DB", dto.getFournisseur().getId());
			throw new EntityNotFoundException(
					"Aucun Fournisseur avec l'ID " + dto.getFournisseur().getId() + " n'a été trouve dans la DB",
					ErrorCodes.FOURNISSEUR_NOT_FOUND);
		}

		List<String> articleErrors = new ArrayList<>();
		if (dto.getLigneCommandeFournisseur() != null) {

			dto.getLigneCommandeFournisseur().forEach(ligneCmdFournisseur -> {
				if (ligneCmdFournisseur.getArticle() != null) {
					Optional<Article> article = articleRepository.findById(ligneCmdFournisseur.getArticle().getId());

					if (article.isEmpty()) {
						articleErrors.add(
								"L'article avec l'ID " + ligneCmdFournisseur.getArticle().getId() + " n'existe pas");
					}
				} else {
					articleErrors.add("Impossible d'enregistrer une commande avec un article NULL");
				}
			});
		}

		if (!articleErrors.isEmpty()) {
			log.warn("");
			throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_VALID,
					articleErrors);
		}

		CommandeFournisseur saveCommandeFournisseur = commandeFournisseurRepository
				.save(CommandeFournisseurDto.toEntity(dto));

		if (dto.getLigneCommandeFournisseur() != null) {
			dto.getLigneCommandeFournisseur().forEach(ligneCmdFournisseur -> {
				LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto
						.toEntity(ligneCmdFournisseur);
				ligneCommandeFournisseur.setCommandeFournisseur(saveCommandeFournisseur);
				ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

			});
		}

		return CommandeFournisseurDto.fromEntity(saveCommandeFournisseur);
	}

	@Override
	public CommandeFournisseurDto findById(Integer id) {
		if (id == null) {
			log.error("Commande fournisseur ID is NULL");
			return null;
		}

		return commandeFournisseurRepository.findById(id).map(CommandeFournisseurDto::fromEntity).orElseThrow(
				() -> new EntityNotFoundException("Aucune commande fournisseur n'a été trouve avec l'ID " + id,
						ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
	}

	@Override
	public CommandeFournisseurDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			log.error("Commande fournisseur CODE is NULL");
			return null;
		}
		return commandeFournisseurRepository.findCommandeFournisseurByCode(code).map(CommandeFournisseurDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException(
						"Aucune commande fournisseur n'a été trouve avec l'CODE " + code,
						ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
	}

	@Override
	public List<CommandeFournisseurDto> findAll() {

		return commandeFournisseurRepository.findAll().stream().map(CommandeFournisseurDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		if(id == null) {
			log.error("Commande fournisseur ID is NULL");
			return;
		}
		List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(id);
	    if (!ligneCommandeFournisseurs.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
	          ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
	    }
		commandeFournisseurRepository.deleteById(id);

	}

	@Override
	public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
		checkIdCommande(idCommande);
	    if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
	      log.error("L'etat de la commande fournisseur is NULL");
	      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
	          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
	    }
	    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
	    commandeFournisseur.setEtatCommande(etatCommande);

	    CommandeFournisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));
	    if (commandeFournisseur.isCommandeLivree()) {
	      updateMvtStk(idCommande);
	    }
	    return CommandeFournisseurDto.fromEntity(savedCommande);
	  }

	@Override
	public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande,
			BigDecimal quantite) {
		checkIdCommande(idCommande);
	    checkIdLigneCommande(idLigneCommande);

	    if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
	      log.error("L'ID de la ligne commande is NULL");
	      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
	          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
	    }

	    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
	    Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

	    LigneCommandeFournisseur ligneCommandeFounisseur = ligneCommandeFournisseurOptional.get();
	    ligneCommandeFounisseur.setQuantite(quantite);
	    ligneCommandeFournisseurRepository.save(ligneCommandeFounisseur);

	    return commandeFournisseur;
	}

	@Override
	public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
		 checkIdCommande(idCommande);
		    if (idFournisseur == null) {
		      log.error("L'ID du fournisseur is NULL");
		      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
		          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
		    }
		    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
		    Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);
		    if (fournisseurOptional.isEmpty()) {
		      throw new EntityNotFoundException(
		          "Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.FOURNISSEUR_NOT_FOUND);
		    }
		    commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

		    return CommandeFournisseurDto.fromEntity(
		        commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
		    );
	}

	@Override
	public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
		checkIdCommande(idCommande);
	    checkIdLigneCommande(idLigneCommande);
	    checkIdArticle(idArticle, "nouvel");

	    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

	    Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(idLigneCommande);

	    Optional<Article> articleOptional = articleRepository.findById(idArticle);
	    if (articleOptional.isEmpty()) {
	      throw new EntityNotFoundException(
	          "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
	    }

	    List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
	    if (!errors.isEmpty()) {
	      throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
	    }

	    LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
	    ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
	    ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

	    return commandeFournisseur;
	}

	@Override
	public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
		checkIdCommande(idCommande);
	    checkIdLigneCommande(idLigneCommande);

	    CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
	    // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
	    findLigneCommandeFournisseur(idLigneCommande);
	    ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

	    return commandeFournisseur;
	}

	@Override
	public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(
			Integer idCommande) {
		return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
		        .map(LigneCommandeFournisseurDto::fromEntity)
		        .collect(Collectors.toList());
	}
	
	private void checkIdCommande(Integer idCommande) {
	    if (idCommande == null) {
	      log.error("Commande fournisseur ID is NULL");
	      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
	          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
	    }
	  }

	private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
	    CommandeFournisseurDto commandeFournisseur = findById(idCommande);
	    if (commandeFournisseur.isCommandeLivree()) {
	      throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
	    }
	    return commandeFournisseur;
	  }
	
	private void checkIdLigneCommande(Integer idLigneCommande) {
	    if (idLigneCommande == null) {
	      log.error("L'ID de la ligne commande is NULL");
	      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
	          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
	    }
	  }
	
	private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
	    Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);
	    if (ligneCommandeFournisseurOptional.isEmpty()) {
	      throw new EntityNotFoundException(
	          "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
	    }
	    return ligneCommandeFournisseurOptional;
	  }
	
	private void checkIdArticle(Integer idArticle, String msg) {
	    if (idArticle == null) {
	      log.error("L'ID de " + msg + " is NULL");
	      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
	          ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
	    }
	  }
	
	private void updateMvtStk(Integer idCommande) {
	    List<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
	    ligneCommandeFournisseur.forEach(lig -> {
	      effectuerEntree(lig);
	    });
	  }

	  private void effectuerEntree(LigneCommandeFournisseur lig) {
	    MvtStkDto mvtStkDto = MvtStkDto.builder()
	        .article(ArticleDto.fromEntity(lig.getArticle()))
	        .dateMvt(Instant.now())
	        .typeMvt(TypeMvtStk.ENTREE)
	        .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
	        .quantite(lig.getQuantite())
	        .idEntreprise(lig.getIdEntreprise())
	        .build();
	    mvtStkService.entreeStock(mvtStkDto);
	  }
}
