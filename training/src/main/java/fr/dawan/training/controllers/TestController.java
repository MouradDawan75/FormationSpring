package fr.dawan.training.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.training.dto.LogDto;
import fr.dawan.training.dto.LogDto.LogLevel;
import fr.dawan.training.dto.LogDto.LogType;
import fr.dawan.training.exceptions.AuthentificationException;

//@Controller -> controller mvc -> renvoie une page html
@RestController
@RequestMapping("/test")
public class TestController {
	
	private static Logger myRootLogger = LoggerFactory.getLogger(TestController.class);
	private static Logger debugLogger = LoggerFactory.getLogger("debugLogger");
	
	
	/*
	 * Rest Controller: doit fournir des ressources (uri - end points) + méthodes d'accès
	 * 
	 * Codes status d'une réponse HTTP:
	 * 1xx: informations fournies par le server
	 * 2xx: succès de la req http
	 * 3xx: redirection
	 * 4xx: erreurs côtés client
	 * 5xx: erreurs côté server
	 * 
	 */
	
	//@RequestMapping(method = RequestMethod.GET, value = "/test/m1", produces = "text/plain")
	//@GetMapping(value = "/test/m1", produces = "text/plain")
	@GetMapping(value = "/m1", produces = "text/plain")
	public String m1() {
		
		myRootLogger.info("Info: appel de m1");
		debugLogger.debug("Debug: m1 ......");
		
		return "m1";
	}
	
	/*
	 * ResponseEntity: permet de choisir le type du contenu renvoyé + personnaliser le code status de la réponse http
	 */
	
	//@RequestMapping(method = RequestMethod.GET, value = "/test/m2", produces = "text/plain")
	//@GetMapping(value = "/test/m2", produces = "text/plain")
	@GetMapping(value = "/m2", produces = "text/plain")
	public ResponseEntity<String> m2(){
		
		//Syntaxe1:
		//return ResponseEntity.ok("m2");
		
		//Syntaxe2: plus lisible
		return ResponseEntity.status(HttpStatus.OK).body("m2");
	}
	
	//Paramètres obligatoires
	
	@GetMapping(value = "/m3/{page}")
	public String m3(@PathVariable(value = "page") int page){
		return "m3 "+page;
	}
	
	//Paramètres optionels - multi uri
	
	@GetMapping(value = {"/m4/{page}", "/m4"})
	public String m4(@PathVariable(value = "page", required = false) Optional<Integer> page) {
		
		if(page.isEmpty()) {
			return "m4";
		}else {
			return "m4 "+page.get();
		}
	}
	
	//Request params - paramètres nommés: localhost:8085/test/m5?page=9 
	//--- le param s'affiche dans l'uri (syntaxe non recommandée)
	
	@GetMapping(value = "/m5")
	public String m5(@RequestParam(value = "page") int page) {
		return "m5 "+page;
	}
	
	//Request Body
	
	@GetMapping(value = "/m6", consumes = "text/plain", produces = "text/plain")
	public String m6(@RequestBody String message) {
		return "m6 "+message;
	}
	
	@GetMapping(value = "ex1")
	public String testException() throws AuthentificationException {
		throw new AuthentificationException("Echec de connexion.....");
	}
	
	/*
	 * Il y'à 2 niveaux de gestion des exceptions:
	 * - Niveau controller: 
	 *     dans chaque définir une méthode dans le controller avec l'annotation @ExceptionHandler(TypeException.class)
	 * - Niveau global:
	 * Gestion des exceptions au niveau de l'application, quelque soit le controller qui a généré l'exception
	 * Créer une classe (intercepteur) avec l'annotation @ControllerAdvice qui hérite de HandlerException
	 * 
	 * 
	 */
	/*
	@ExceptionHandler(AuthentificationException.class)
	public ResponseEntity<LogDto> excepHandler() {
		
		LogDto logDto = new LogDto();
		logDto.setCode(403);
		logDto.setMessage("Echec de connexion....");
		logDto.setLogType(LogType.ACCESS);
		logDto.setLogLevel(LogLevel.ERROR);
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(logDto);
	}
	*/

	@GetMapping(value = "ex2")
	public String testGeneriqueException() throws Exception {
		throw new NullPointerException("Server Error.....");
	}
	
	/*
	@ExceptionHandler(Exception.class)
	public ResponseEntity<LogDto> excepGenericHandler() {
		
		LogDto logDto = new LogDto();
		logDto.setCode(502);
		logDto.setMessage("Server Error....");
		logDto.setLogType(LogType.EXCEPTION);
		logDto.setLogLevel(LogLevel.ERROR);
		
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(logDto);
	}
	*/
}
