package fr.dawan.training.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.dawan.training.tools.JwtTokenTool;
import fr.dawan.training.tools.TokenSaver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor{
	
	@Autowired
	private JwtTokenTool jwtTokenTool;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//Gestion des uri publiques et privées
		
		if( !publiqueUrls().contains(request.getRequestURI()) && !request.getRequestURI().contains("/swagger")) {
			
			//Vérification du Token
			System.out.println(">>>> Uri: "+request.getRequestURI());
			
			String header = request.getHeader("Authorization");
			
			if(header == null || header.length() < 7 || header.trim().equals("")) {
				
				//throw new Exception("Token invalide.....");
				response.setStatus(403);
				response.getWriter().write("Token invalide.....");
				return false;
			}
			
			String token = header.substring(7); // suppression du mot Bearer suivi d'un éspace
			
			//Vérifier si token à expiré
			
			if(jwtTokenTool.isTokenExpired(token)) {
				response.setStatus(403);
				response.getWriter().write("Token invalide.....");
				return false;
			}
			
			//Comparer le token fournit au token sauvegardé côté serveur
			
			String email = jwtTokenTool.getUsernameFromToken(token);
			
			if(!TokenSaver.tokenByEmail.containsKey(email) || !TokenSaver.tokenByEmail.get(email).equals(token)) {
				response.setStatus(403);
				response.getWriter().write("Token invalide.....");
				return false;
			}
			
		}
		
		
		return true;
	}
	
	private List<String> urls = new ArrayList<>();
	
	public List<String> publiqueUrls(){
		urls.add("/login"); // connexion
		urls.add("/api/users/save"); //création de compte
		urls.add("/api-docs"); //docs json swagger
		
		return urls;
	}

}
