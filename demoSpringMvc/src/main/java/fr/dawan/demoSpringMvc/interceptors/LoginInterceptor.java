package fr.dawan.demoSpringMvc.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getRequestURI().contains("/produits/") || request.getRequestURI().contains("/utilisateurs/")) {
			
			//vérifier si user est connecté
			if(request.getSession().getAttribute("email") == null) {
				
				//Redirection vers la page de connexion
				//request.getContextPath(): renvoie domaine:port
				response.sendRedirect(request.getContextPath()+"/login");
			}
		}
		
		return true;
	}
}
