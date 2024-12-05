package fr.dawan.training.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor{
	
	//Toutes les requêtes HTTP seront interceptées par cet intercepteur

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(">>>>> à l'intérieur de MyInterceptor..........");
		
		return true; // req. transmise à l'api
	}

	
}
