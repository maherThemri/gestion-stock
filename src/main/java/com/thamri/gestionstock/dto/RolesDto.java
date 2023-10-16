package com.thamri.gestionstock.dto;

import com.thamri.gestionstock.model.Roles;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class RolesDto {
	
	private Integer id;

	private String roleName;

	private UtilisateurDto utilisateur;
	
	public static RolesDto fromEntity (Roles roles) {
		if(roles==null) {
			return null;
		}
		
		return RolesDto.builder()
				.id(roles.getId())
				.roleName(roles.getRoleName())
				.utilisateur(UtilisateurDto.fromEntity(roles.getUtilisateur()))
				.build();
	}
	
	public static Roles toEntity (RolesDto rolesDto) {
		
		if (rolesDto==null) {
			return null;
		}
		Roles roles=new Roles();
		roles.setId(rolesDto.getId());
		roles.setRoleName(rolesDto.getRoleName());
		roles.setUtilisateur(UtilisateurDto.toEntity(rolesDto.getUtilisateur()));
		
		return roles;
	}
}
