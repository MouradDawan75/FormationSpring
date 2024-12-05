package fr.dawan.demoSpringMvc.interceptors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String displayErrorPage() {
		return "error";
	}
	
	/*
	 * Autre option possible, définir une méthode pour gérer chaque type d'exception
	 * 
	 * @ExceptionHandler(NulPointerException.class)
	public String displayErrorPage1() {
		return "error1";
	}
	 */
}
