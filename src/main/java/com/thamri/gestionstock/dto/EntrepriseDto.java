package com.thamri.gestionstock.dto;

import java.util.List;

import com.thamri.gestionstock.model.Entreprise;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class EntrepriseDto {
	
	private Integer id;

	private String nom;

	private String description;

	private AdresseDto adresse;

	private String CodeFiscal;

	private String photo;

	private String email;

	private String numTel;

	private String steWeb;

	private List<UtilisateurDto> utilisateur;
	
	public static EntrepriseDto fromEntity(Entreprise entreprise) {
		if(entreprise==null) {
			return null;
		}
		
		return EntrepriseDto.builder()
				.id(entreprise.getId())
				.nom(entreprise.getNom())
				.description(entreprise.getDescription())
				.adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
				.CodeFiscal(entreprise.getCodeFiscal())
				.photo(entreprise.getPhoto())
				.email(entreprise.getEmail())
				.numTel(entreprise.getNumTel())
				.steWeb(entreprise.getSteWeb())
				.build();
	}
	public static Entreprise toEntity(EntrepriseDto entrepriseDto) {
		if(entrepriseDto==null) {
			return null;
		}
		Entreprise entreprise= new Entreprise();
		entreprise.setId(entrepriseDto.getId());
		entreprise.setNom(entrepriseDto.getNom());
		entreprise.setDescription(entrepriseDto.getDescription());
		entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
		entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
		entreprise.setEmail(entrepriseDto.getEmail());
		entreprise.setPhoto(entrepriseDto.getPhoto());
		entreprise.setNumTel(entrepriseDto.getNumTel());
		entreprise.setSteWeb(entrepriseDto.getSteWeb());
		
		return entreprise;
	}
}
