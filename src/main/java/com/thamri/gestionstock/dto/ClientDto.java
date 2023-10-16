package com.thamri.gestionstock.dto;

import java.util.List;

import com.thamri.gestionstock.model.Client;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class ClientDto {
	
	private Integer id;

	private String nom;

	private String prenom;

	private AdresseDto adresse;

	private String photo;

	private String mail;

	private String numTel;

	private List<CommandeClientDto> commandeClients;
	
	public static ClientDto fromEntity (Client client ) {
		
		if (client == null) {
			return null;
			//TODO throw an exception
		}
		
		return ClientDto.builder()
				.id(client.getId())
				.nom(client.getNom())
				.prenom(client.getPrenom())
				.photo(client.getPhoto())
				.mail(client.getMail())
				.numTel(client.getNumTel())
				.adresse(AdresseDto.fromEntity(client.getAdresse()))
				.build();
	}
	
	public static Client toEntity (ClientDto clientDto) {
		if(clientDto==null) {
			return null;
			//TODO throw an exception
		}
		Client client = new Client();
		client.setId(clientDto.getId());
		client.setNom(clientDto.getNom());
		client.setPrenom(clientDto.getPrenom());
		client.setMail(clientDto.getMail());
		client.setPhoto(clientDto.getPhoto());
		client.setNumTel(clientDto.getNumTel());
		client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
		
		return client;
	}
}
