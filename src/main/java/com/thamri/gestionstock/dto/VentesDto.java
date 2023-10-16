package com.thamri.gestionstock.dto;

import java.time.Instant;
import java.util.List;

import com.thamri.gestionstock.model.Ventes;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class VentesDto {
	
	private Integer id;
	
	private String code;

	private Instant dateVente;

	private String commantaire;
	
	private List<LigneVenteDto> ligneVente;
	
	public static VentesDto fromEntity(Ventes ventes) {
		if(ventes==null) {
			return null;
		}
		
		return VentesDto.builder()
				.id(ventes.getId())
				.code(ventes.getCode())
				.dateVente(ventes.getDateVente())
				.commantaire(ventes.getCommantaire())
				.build();
	}
	
	public static Ventes toEntity (VentesDto ventesDto) {
		if(ventesDto == null) {
			return null;
		}
		Ventes ventes = new Ventes();
		ventes.setId(ventesDto.getId());
		ventes.setCode(ventesDto.getCode());
		ventes.setDateVente(ventesDto.getDateVente());
		ventes.setCommantaire(ventesDto.getCommantaire());
		
		return ventes;
	}
}
