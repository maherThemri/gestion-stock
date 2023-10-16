package com.thamri.gestionstock.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thamri.gestionstock.dto.FournisseurDto;
import com.thamri.gestionstock.exception.EntityNotFoundException;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidEntityException;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.model.CommandeFournisseur;
import com.thamri.gestionstock.repository.CommandeFournisseurRepository;
import com.thamri.gestionstock.repository.FournisseurRepository;
import com.thamri.gestionstock.services.FournisseurService;
import com.thamri.gestionstock.validator.FournisseurValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

	private FournisseurRepository fournisseurRepository;

	private CommandeFournisseurRepository commandeFournisseurRepository;

	@Autowired
	public FournisseurServiceImpl(FournisseurRepository fournisseurRepository,
			CommandeFournisseurRepository commandeFournisseurRepository) {
		this.commandeFournisseurRepository = commandeFournisseurRepository;
		this.fournisseurRepository = fournisseurRepository;
	}

	@Override
	public FournisseurDto save(FournisseurDto dto) {
		List<String> errors = FournisseurValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Fournisseur is not valid", dto);
			throw new InvalidEntityException("", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
		}
		return FournisseurDto.fromEntity(fournisseurRepository.save(FournisseurDto.toEntity(dto)));
	}

	@Override
	public List<FournisseurDto> findAll() {
		
		return fournisseurRepository.findAll()
				.stream()
				.map(FournisseurDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public FournisseurDto findById(Integer id) {
		if(id == null) {
			log.error("Fournisseur ID is NULL");
			return null;
		}
		
		return fournisseurRepository.findById(id)
				.map(FournisseurDto::fromEntity)
				.orElseThrow(()-> new EntityNotFoundException("Aucun fournisseur avec l'ID" + id + " n' ete trouve dans la BDD",
						ErrorCodes.FOURNISSEUR_NOT_FOUND));
	}

	@Override
	public void delete(Integer id) {
		if(id == null) {
			log.error("Fournisseur ID is NULL");
			return;
		}
		List<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findAllByFournisseurId(id);
	    if (!commandeFournisseur.isEmpty()) {
	      throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
	          ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
	    }
		fournisseurRepository.deleteById(id);

	}

}
