package fr.dawan.demoSpringMvc.interceptors;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice //pour la prod
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String displayErrorPage(Exception ex,Model model) {
		
		model.addAttribute("exMessage", ex.getMessage());
		//model.addAttribute("trace", ex.getStackTrace().toString()); -> pour le dev
		
		return "error";
	}
	
	/*
	 * Dans un environnement dev:
	 * Pour afficher les traces des erreurs:
	 * 1- ajouter dans application.properties
	 * server.error.include-stacktrace=always
	 * Créer la page error dans template et choisir les infos à afficher
	 * 
	 */
	
	/*
	 * Autre option possible, définir une méthode pour gérer chaque type d'exception
	 * 
	 * @ExceptionHandler(NulPointerException.class)
	public String displayErrorPage1() {
		return "error1";
	}
	 */
}
