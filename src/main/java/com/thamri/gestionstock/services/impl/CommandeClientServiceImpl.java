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
import com.thamri.gestionstock.dto.ClientDto;
import com.thamri.gestionstock.dto.CommandeClientDto;
import com.thamri.gestionstock.dto.LigneCommandeClientDto;
import com.thamri.gestionstock.dto.MvtStkDto;
import com.thamri.gestionstock.exception.EntityNotFoundException;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidEntityException;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.model.Article;
import com.thamri.gestionstock.model.Client;
import com.thamri.gestionstock.model.CommandeClient;
import com.thamri.gestionstock.model.EtatCommande;
import com.thamri.gestionstock.model.LigneCommandeClient;
import com.thamri.gestionstock.model.SourceMvtStk;
import com.thamri.gestionstock.model.TypeMvtStk;
import com.thamri.gestionstock.repository.ArticleRepository;
import com.thamri.gestionstock.repository.ClientRepository;
import com.thamri.gestionstock.repository.CommandeClientRepository;
import com.thamri.gestionstock.repository.LigneCommandeClientRepository;
import com.thamri.gestionstock.services.CommandeClientService;
import com.thamri.gestionstock.services.MvtStkService;
import com.thamri.gestionstock.validator.ArticleValidator;
import com.thamri.gestionstock.validator.CommandeClientValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

	private CommandeClientRepository commandeClientRepository;
	private LigneCommandeClientRepository ligneCommandeClientRepository;
	private ClientRepository clientRepository;
	private ArticleRepository articleRepository;
	private MvtStkService mvtStkService;

	@Autowired
	public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository,
			ClientRepository clientRepository, ArticleRepository articleRepository,
			LigneCommandeClientRepository ligneCommandeClientRepository, MvtStkService mvtStkService) {

		this.commandeClientRepository = commandeClientRepository;
		this.ligneCommandeClientRepository = ligneCommandeClientRepository;
		this.clientRepository = clientRepository;
		this.articleRepository = articleRepository;
		this.mvtStkService = mvtStkService;
	}

	@Override
	public CommandeClientDto save(CommandeClientDto dto) {
		List<String> errors = CommandeClientValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Commande client is not valide {}", dto);
			throw new InvalidEntityException("Commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID,
					errors);
		}

		if (dto.getId() != null && dto.isCommandeLivree()) {
			throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle  est livree",
					ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		}

		Optional<Client> client = clientRepository.findById(dto.getClient().getId());
		if (client.isEmpty()) {
			log.warn("Client with Id {} was not found in the DB", dto.getClient().getId());
			throw new EntityNotFoundException(
					"Aucun Client avec l'ID " + dto.getClient().getId() + " n'a été trouve dans la DB",
					ErrorCodes.CLIENT_NOT_FOUND);
		}

		List<String> articleErrors = new ArrayList<>();
		if (dto.getLigneCommandeClients() != null) {

			dto.getLigneCommandeClients().forEach(ligneCmdCleint -> {
				if (ligneCmdCleint.getArticle() != null) {
					Optional<Article> article = articleRepository.findById(ligneCmdCleint.getArticle().getId());

					if (article.isEmpty()) {
						articleErrors
								.add("L'article avec l'ID " + ligneCmdCleint.getArticle().getId() + " n'existe pas");
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

		CommandeClient saveCommandeClient = commandeClientRepository.save(CommandeClientDto.toEntity(dto));

		if (dto.getLigneCommandeClients() != null) {
			dto.getLigneCommandeClients().forEach(ligneCmdClient -> {
				LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(ligneCmdClient);
				ligneCommandeClient.setCommandeClient(saveCommandeClient);
				ligneCommandeClientRepository.save(ligneCommandeClient);

			});
		}

		return CommandeClientDto.fromEntity(saveCommandeClient);
	}

	@Override
	public CommandeClientDto findById(Integer id) {

		if (id == null) {
			log.error("Commande Client ID is NULL");
			return null;
		}

		return commandeClientRepository.findById(id).map(CommandeClientDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException("Aucune commande client n'a été trouve avec l'ID " + id,
						ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
	}

	@Override
	public CommandeClientDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			log.error("Commande Client CODE is NULL");
			return null;
		}
		return commandeClientRepository.findCommandeClientByCode(code).map(CommandeClientDto::fromEntity).orElseThrow(
				() -> new EntityNotFoundException("Aucune commande client n'a été trouve avec l'CODE " + code,
						ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
	}

	@Override
	public List<CommandeClientDto> findAll() {

		return commandeClientRepository.findAll().stream().map(CommandeClientDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Commande Client ID is NULL");
			return;
		}
		List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
	    if (!ligneCommandeClients.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer une commande client deja utilisee",
	          ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
	    }
	    commandeClientRepository.deleteById(id);

	}

	@Override
	public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {

		if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
		      log.error("L'etat de la commande client is NULL");
		      throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
		          ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		    }
		    CommandeClientDto commandeClient = checkEtatCommande(idCommande);
		    commandeClient.setEtatCommande(etatCommande);

		    CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient));
		    if (commandeClient.isCommandeLivree()) {
		      updateMvtStk(idCommande);
		    }

		    return CommandeClientDto.fromEntity(savedCmdClt);
		  }

	@Override
	public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {

		checkIdCommande(idCommande);

		checkIdLigneCommande(idLigneCommande);

		if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
			log.error("Id Ligne Commande of client  is NULL");
			throw new InvalidOperationException(
					"Impossible de modifier l'etat de commande avec une quantite de commande null ou zero",
					ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		}

		CommandeClientDto commandeClient = checkEtatCommande(idCommande);
		Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);
		LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
		ligneCommandeClient.setQuantite(quantite);
		ligneCommandeClientRepository.save(ligneCommandeClient);
		return commandeClient;
	}

	@Override
	public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {

		checkIdCommande(idCommande);

		CommandeClientDto commandeClient = checkEtatCommande(idCommande);

		Optional<Client> clientOptional = clientRepository.findById(idClient);
		if (clientOptional.isEmpty()) {
			throw new EntityNotFoundException("Aucune client n'a été trouve avec l'ID " + idClient,
					ErrorCodes.CLIENT_NOT_FOUND);
		}

		commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));
		return CommandeClientDto.fromEntity(commandeClientRepository.save(CommandeClientDto.toEntity(commandeClient)));
	}

	@Override
	public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {

		checkIdCommande(idCommande);
		checkIdLigneCommande(idLigneCommande);
		checkIdArticle(idArticle);
		CommandeClientDto commandeClient = checkEtatCommande(idCommande);
		Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);
		Optional<Article> articleOptional = articleRepository.findById(idArticle);
		if (articleOptional.isEmpty()) {
			throw new EntityNotFoundException("Aucune article n'a été trouve avec l'ID " + idArticle,
					ErrorCodes.ARTICLE_NOT_FOUND);
		}
		List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("", ErrorCodes.ARTICLE_NOT_VALID, errors);
		}
		LigneCommandeClient ligneCommandeClientToSave = ligneCommandeClient.get();
		ligneCommandeClientToSave.setArticle(articleOptional.get());
		ligneCommandeClientRepository.save(ligneCommandeClientToSave);
		return commandeClient;
	}

	@Override
	public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {

		checkIdCommande(idCommande);
		checkIdLigneCommande(idLigneCommande);

		CommandeClientDto commandeClient = checkEtatCommande(idCommande);
		// Just To check the ligneCommandeClient and inform the client in case it is
		// absent
		findLigneCommandeClient(idLigneCommande);
		ligneCommandeClientRepository.deleteById(idLigneCommande);

		return commandeClient;
	}

	@Override
	public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
		return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
				.map(LigneCommandeClientDto::fromEntity).collect(Collectors.toList());
	}

	private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
		Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository
				.findById(idLigneCommande);
		if (ligneCommandeClientOptional.isEmpty()) {
			throw new EntityNotFoundException(
					"Aucune ligne commande client n'a été trouve avec l'ID " + idLigneCommande,
					ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
		}
		return ligneCommandeClientOptional;
	}

	private CommandeClientDto checkEtatCommande(Integer idCommande) {
		CommandeClientDto commandeClient = findById(idCommande);
		if (commandeClient.isCommandeLivree()) {
			throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle  est livree",
					ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		}
		return commandeClient;
	}

	private void checkIdCommande(Integer idCommande) {
		if (idCommande == null) {
			log.error("Commande Client ID is NULL");
			throw new InvalidOperationException("Impossible de modifier l'etat de commande lorsque L'ID est NULL",
					ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		}
	}

	private void checkIdLigneCommande(Integer idLigneCommande) {
		if (idLigneCommande == null) {
			log.error("Id Ligne Commande of client  is NULL");
			throw new InvalidOperationException("Impossible de modifier l'etat de commande avec ligne de commande null",
					ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		}
	}

	private void checkIdArticle(Integer idArticle) {
		if (idArticle == null) {
			log.error("Id Article  is NULL");
			throw new InvalidOperationException("Impossible de modifier l'etat de commande avec un ID article null",
					ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
		}
	}

	private void updateMvtStk(Integer idCommande) {
		List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository
				.findAllByCommandeClientId(idCommande);
		ligneCommandeClients.forEach(lig -> {
			effectuerSortie(lig);
		});
	}

	private void effectuerSortie(LigneCommandeClient lig) {
		MvtStkDto mvtStkDto = MvtStkDto.builder().article(ArticleDto.fromEntity(lig.getArticle()))
				.dateMvt(Instant.now()).typeMvt(TypeMvtStk.SORTIE).sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
				.quantite(lig.getQuantite()).idEntreprise(lig.getIdEntreprise()).build();
		mvtStkService.sortieStock(mvtStkDto);
	}
}
