package com.thamri.gestionstock.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thamri.gestionstock.dto.EntrepriseDto;
import com.thamri.gestionstock.dto.RolesDto;
import com.thamri.gestionstock.dto.UtilisateurDto;
import com.thamri.gestionstock.exception.EntityNotFoundException;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidEntityException;
import com.thamri.gestionstock.repository.EntrepriseRepository;
import com.thamri.gestionstock.repository.RolesRepository;
import com.thamri.gestionstock.services.EntrepriseService;
import com.thamri.gestionstock.services.UtilisateurService;
import com.thamri.gestionstock.validator.EntrepriseValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

	private EntrepriseRepository entrepriseRepository;
	private UtilisateurService utilisateurService;
	private RolesRepository rolesRepository;

	@Autowired
	public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
		this.entrepriseRepository = entrepriseRepository;
//	    this.utilisateurService = utilisateurService;
//	    this.rolesRepository = rolesRepository;
	}

	@Override
	public EntrepriseDto save(EntrepriseDto dto) {
		List<String> errors = EntrepriseValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Entreprise is not valid {}", dto);
			throw new InvalidEntityException("L'entreprise n'est pas valide",
					ErrorCodes.ENTREPRISE_NOT_VALID, errors);
		}
		EntrepriseDto savedEntreprise = EntrepriseDto
				.fromEntity(entrepriseRepository.save(EntrepriseDto.toEntity(dto)));

		UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);

		UtilisateurDto savedUser = utilisateurService.save(utilisateur);

		RolesDto rolesDto = RolesDto.builder().roleName("ADMIN").utilisateur(savedUser).build();

		rolesRepository.save(RolesDto.toEntity(rolesDto));

		return savedEntreprise;
	}

	private UtilisateurDto fromEntreprise(EntrepriseDto dto) {
		return UtilisateurDto.builder().adresse(dto.getAdresse()).nom(dto.getNom()).prenom(dto.getCodeFiscal())
				.email(dto.getEmail()).moteDePasse(generateRandomPassword()).entreprise(dto)
				.dateDeNaissance(Instant.now()).photo(dto.getPhoto()).build();
	}

	private String generateRandomPassword() {
		return "som3R@nd0mP@$$word";
	}

	@Override
	public List<EntrepriseDto> findAll() {
		return entrepriseRepository.findAll().stream().map(EntrepriseDto::fromEntity).
				collect(Collectors.toList());
	}

	@Override
	public EntrepriseDto findById(Integer id) {
		if (id == null) {
			log.error("Entreprise ID is null");
			return null;
		}
		return entrepriseRepository.findById(id).map(EntrepriseDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException(
						"Aucune entreprise avec l'ID = " + id + " n' ete trouve dans la BDD",
						ErrorCodes.ENTREPRISE_NOT_FOUND));
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Entreprise ID is null");
			return;
		}
		entrepriseRepository.deleteById(id);

	}

}
