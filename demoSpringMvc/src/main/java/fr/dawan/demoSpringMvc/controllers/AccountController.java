package fr.dawan.demoSpringMvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/create-account")
	public String createAccount() {
		return "create-account";
	}

}
