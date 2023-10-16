package com.thamri.gestionstock.services.strategy;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flickr4java.flickr.FlickrException;
import com.thamri.gestionstock.dto.EntrepriseDto;
import com.thamri.gestionstock.exception.ErrorCodes;
import com.thamri.gestionstock.exception.InvalidOperationException;
import com.thamri.gestionstock.services.EntrepriseService;
import com.thamri.gestionstock.services.FlickrService;

import lombok.extern.slf4j.Slf4j;

@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

	private FlickrService flickrService;
	private EntrepriseService entrepriseService;
	
	@Autowired
	public SaveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
		this.flickrService = flickrService;
		this.entrepriseService = entrepriseService;
	}
	@Override
	public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
		
		EntrepriseDto entreprise = entrepriseService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if(!StringUtils.hasLength(urlPhoto)) {
			throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		entreprise.setPhoto(urlPhoto);
		return entrepriseService.save(entreprise);
	}

}
