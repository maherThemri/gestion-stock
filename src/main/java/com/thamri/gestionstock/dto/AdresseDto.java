package com.thamri.gestionstock.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.thamri.gestionstock.model.Adresse;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class AdresseDto {

	/*
	 * @Autowired private static ModelMapper modelMapper;
	 */

	private String adresse1;

	private String adresse2;

	private String ville;

	private String codePostale;

	private String pays;

	public static AdresseDto fromEntity(Adresse adresse) {

		if (adresse == null) {
			return null;
			// TODO an exception
		}

		return AdresseDto.builder()
				.adresse1(adresse.getAdresse1())
				.adresse2(adresse.getAdresse2())
				.codePostale(adresse.getCodePostale())
				.pays(adresse.getPays())
				.ville(adresse.getVille())
				.build();
		/*
		 * AdresseDto adresseDto = modelMapper.map(adresse, AdresseDto.class); return
		 * adresseDto;
		 */

	}

	public static Adresse toEntity(AdresseDto adresseDto) {

		if (adresseDto == null) {
			return null;
			// TODO an exception
		}

		Adresse adresse = new Adresse();
		adresse.setAdresse1(adresseDto.getAdresse1());
		adresse.setAdresse2(adresseDto.getAdresse2());
		adresse.setCodePostale(adresseDto.getCodePostale());
		adresse.setPays(adresseDto.getPays());
		adresse.setVille(adresseDto.getVille());

		return adresse;
	}
}
