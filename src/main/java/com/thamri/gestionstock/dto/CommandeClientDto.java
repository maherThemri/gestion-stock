package com.thamri.gestionstock.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thamri.gestionstock.model.CommandeClient;
import com.thamri.gestionstock.model.EtatCommande;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class CommandeClientDto {
	
	private Integer id;

	private String code;

	private Instant dateCommande;
	
	private EtatCommande etatCommande;

	private Integer idEntreprise;

	private ClientDto client;

	@JsonIgnore
	private List<LigneCommandeClientDto> ligneCommandeClients;
	
	public static CommandeClientDto fromEntity(CommandeClient commandeClient) {
		
		if(commandeClient==null) {
			return null;
		}
		
		return CommandeClientDto.builder()
				.id(commandeClient.getId())
				.code(commandeClient.getCode())
				.dateCommande(commandeClient.getDateCommande())
				.client(ClientDto.fromEntity(commandeClient.getClient()))
				.etatCommande(commandeClient.getEtatCommande())
				.idEntreprise(commandeClient.getIdEntreprise())
				.build();
	}
	
	public static CommandeClient toEntity(CommandeClientDto commandeClientDto) {
		if(commandeClientDto==null) {
			return null;
		}
		
		CommandeClient commandeClient=new CommandeClient();
		commandeClient.setId(commandeClientDto.getId());
		commandeClient.setCode(commandeClientDto.getCode());
		commandeClient.setDateCommande(commandeClientDto.getDateCommande());
		commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
		commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
	    commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));

		
		return commandeClient;
	}
	
	public Boolean isCommandeLivree() {
		return EtatCommande.LIVREE.equals(this.etatCommande);
	}
}
