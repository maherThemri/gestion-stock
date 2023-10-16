package com.thamri.gestionstock.services.strategy;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;
import com.thamri.gestionstock.dto.UtilisateurDto;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.services.FlickrService;
import com.thamri.gestionstock.services.UtilisateurService;

import lombok.extern.slf4j.Slf4j;

@Service("utilisateurStrategy")
@Slf4j
public class SaveUtilisateurPhoto implements Strategy<UtilisateurDto> {

	private FlickrService flickrService;
	private UtilisateurService utilisateurService;
	 
	@Autowired
	public SaveUtilisateurPhoto(FlickrService flickrService, UtilisateurService utilisateurService) {
		
		this.flickrService = flickrService;
		this.utilisateurService = utilisateurService;
	}


	@Override
	public UtilisateurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
		UtilisateurDto utilisateur = utilisateurService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if(!StringUtils.hasLength(urlPhoto)) {
			
			throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du utilisateur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		utilisateur.setPhoto(urlPhoto);
		return utilisateurService.save(utilisateur);
	}


}
