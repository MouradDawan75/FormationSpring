package fr.dawan.training.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.dawan.training.dto.LocationDto;

@Service
public class LocationServiceImpl implements ILocationService{
	
	/*
	 * Pour faire appel à des Apis externes, Spring propose la classe RestTemplate permettant d'exécuter des
	 * requêtes HTTP et de traiter les réponses HTTP renvoyées par les Apis externes
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	private String Base_URL = "https://dawan.org/public/location";

	@Override
	public List<LocationDto> importFromExterneSystem() throws Exception {  
		/*
		 * getForObject: renvoie uniquement le contenu du body
		 * getForEntity: renvoie ResponseEntity: contient le code de la réponse + le contenu body
		 */
		
		ResponseEntity<LocationDto[]> reponse = restTemplate.getForEntity(Base_URL, LocationDto[].class);
		if(reponse.getStatusCode() == HttpStatus.OK && reponse.hasBody()) {
			LocationDto[] tab = reponse.getBody();
			
			//Traitements spécifiques pour les données renvoyées, ensuite faire un return
			return Arrays.asList(tab);
		}
		
		return new ArrayList<LocationDto>();
	}

}
