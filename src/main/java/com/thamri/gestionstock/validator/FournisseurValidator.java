package com.thamri.gestionstock.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.thamri.gestionstock.dto.FournisseurDto;

public class FournisseurValidator {
	
public static List<String> validate(FournisseurDto dto){
		
		List<String> errors = new ArrayList<>();
		
		if(dto == null) {
			errors.add("veuillez renseigner le nom du fournisseur");
			errors.add("veuillez renseigner le prenom du fournisseur");
			errors.add("veuillez renseigner le mail du fournisseur");
			errors.add("veuillez renseigner le N° téléphone du fournisseur");
			
			return errors;
		}
		
		if(!StringUtils.hasLength(dto.getNom())) {
			errors.add("veuillez renseigner le nom du fournisseur");
		}
		if(!StringUtils.hasLength(dto.getPrenom())) {
			errors.add("veuillez renseigner le prenom du fournisseur");
		}
		if(!StringUtils.hasLength(dto.getMail())) {
			errors.add("veuillez renseigner le mail du fournisseur");
		}
		if(!StringUtils.hasLength(dto.getNumTel())) {
			errors.add("veuillez renseigner le N° téléphone du fournisseur");
		}
		
		
		return errors;
	}

}
