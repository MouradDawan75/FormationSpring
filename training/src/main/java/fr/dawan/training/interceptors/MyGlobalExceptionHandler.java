package fr.dawan.training.interceptors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.dawan.training.dto.LogDto;
import fr.dawan.training.dto.LogDto.LogLevel;
import fr.dawan.training.dto.LogDto.LogType;
import fr.dawan.training.exceptions.AuthentificationException;

@ControllerAdvice
public class MyGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	//On peut définir des méthodes qui capturent des exceptions en fonction de leur types
	
	@ExceptionHandler(value = AuthentificationException.class)
	public ResponseEntity<?> handleAuthentificationException(Exception ex, WebRequest request){
		
		LogDto logDto = new LogDto();
		logDto.setCode(401);
		logDto.setMessage("Echec de connexion....");
		logDto.setLogType(LogType.ACCESS);
		logDto.setLogLevel(LogLevel.ERROR);
		logDto.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, logDto, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
	
	
	//Méthode pour capturer les exceptions non gérées par une méthode
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> handleGenericException(Exception ex, WebRequest request){
		
		LogDto logDto = new LogDto();
		logDto.setCode(500);
		logDto.setMessage("Internal Error.....");
		logDto.setLogType(LogType.EXCEPTION);
		logDto.setLogLevel(LogLevel.ERROR);
		logDto.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
		
		return handleExceptionInternal(ex, logDto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
