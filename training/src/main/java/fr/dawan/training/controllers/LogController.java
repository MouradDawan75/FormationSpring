package fr.dawan.training.controllers;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dawan.training.dto.LogDto;

@RestController
@RequestMapping("/api/logs")
public class LogController {
	
	private static Logger rootLogger = LoggerFactory.getLogger(LogController.class);
	private static Logger debugLogger = LoggerFactory.getLogger("debugLogger");
	
	/*
	 * Controller destiné aux admins: pour consulter les logs à distance
	 * @Value: permet de récupérer les params déclarés dans application.properties
	 */
	
	@Value("${storage.folder}")
	private String storageFolder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@GetMapping(value = "/{filename}", produces = "application/octet-stream")
	public ResponseEntity<Resource> getLogFile(@PathVariable(value = "filename") String fileName) throws Exception{
		
		File f = new File(storageFolder+"/"+fileName);
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
		
		//Retourner le résultat
		//Pour afficher une boite de dialogue de téléchargement dans une réponse web au lieu de charger le contenu dans 
		// dans la page (body de la réponse), nous devons spécifier un header pour réponse contenant:
		// Content-disposition, attachment;filename=app.log
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName);
		
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(f.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
		
	}
	
	//Méthode pour insérer un log dans le fichier des logs
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> postLog(@RequestBody LogDto logDto) throws Exception{
		
		//Convertir logDto en chaine pour l'insérer dans le fichier des logs
		/*
		 *ObjectMapper: conversion d'un objet en String et vice versa -> 
		 *    contenu dans la dépendance Jackson utilisée par Spring pour les sérialisation JSON
		 *ModelMapper: conversion d'un dto vers entity et vice versa
		 *
		 */
		
		String logString = objectMapper.writeValueAsString(logDto);
		
		switch (logDto.getLogLevel()){
		case INFO: 
			rootLogger.info(logString);
			break;
			
		case WARNING: 
			rootLogger.warn(logString);
			break;
			
		case ERROR: 
			rootLogger.error(logString);
			break;
			
		case DEBUG: 
			debugLogger.debug(logString);
			break;
			
		default:
			break;
			
		}
		
		return ResponseEntity.ok().build(); //renvoie un body vide
		
	}

}
