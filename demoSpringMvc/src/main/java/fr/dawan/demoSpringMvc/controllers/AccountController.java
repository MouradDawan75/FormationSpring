package fr.dawan.demoSpringMvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.dawan.demoSpringMvc.entities.Utilisateur;
import fr.dawan.demoSpringMvc.services.IUtilisateurService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	
	@Autowired
	private IUtilisateurService utilisateurService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/create-account")
	public String createAccount(Model model) {
		model.addAttribute("success", false);
		return "create-account";
	}
	
	
	@PostMapping("/create-account")
	public String createAccount(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("file") MultipartFile file, Model model) throws Exception {
		
		Utilisateur u = new Utilisateur(email, password, false, file.getBytes());
		utilisateurService.saveOrUpdate(u);
		model.addAttribute("success", true);
		
		return "create-account";
	}
	
	@PostMapping("/authentication")
	public String authentication(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model, HttpSession session) throws Exception {
		
		Utilisateur u = utilisateurService.checkLogin(email, password);
		if(u != null) {
			//connexion ok
			session.setAttribute("user", u);
			session.setAttribute("userImage", u.getBase64Image());
			session.setAttribute("email", u.getEmail());
			
			if(u.isAdmin()) {
				session.setAttribute("admin", u.isAdmin());
			}
			
			
			return "redirect:/";
			
		}
		
		model.addAttribute("errorMessage", "Echec connexion.....");
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
	

}
