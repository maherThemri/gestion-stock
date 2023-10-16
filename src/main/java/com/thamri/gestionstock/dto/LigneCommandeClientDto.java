package com.thamri.gestionstock.dto;

import java.math.BigDecimal;

import com.thamri.gestionstock.model.LigneCommandeClient;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class LigneCommandeClientDto {
	
	private Integer id;

	private ArticleDto article;

	private CommandeClientDto commandeClient;

	private BigDecimal quantite;

	private BigDecimal prixUnitaire;
	
	private Integer idEntreprise;

	  public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient) {
	    if (ligneCommandeClient == null) {
	      return null;
	    }
	    return LigneCommandeClientDto.builder()
	        .id(ligneCommandeClient.getId())
	        .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
	        .quantite(ligneCommandeClient.getQuantite())
	        .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
	        .idEntreprise(ligneCommandeClient.getIdEntreprise())
	        .build();
	  }

	  public static LigneCommandeClient toEntity(LigneCommandeClientDto dto) {
	    if (dto == null) {
	      return null;
	    }

	    LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
	    ligneCommandeClient.setId(dto.getId());
	    ligneCommandeClient.setArticle(ArticleDto.toEntity(dto.getArticle()));
	    ligneCommandeClient.setPrixUnitaire(dto.getPrixUnitaire());
	    ligneCommandeClient.setQuantite(dto.getQuantite());
	    ligneCommandeClient.setIdEntreprise(dto.getIdEntreprise());
	    return ligneCommandeClient;
	  }
}
