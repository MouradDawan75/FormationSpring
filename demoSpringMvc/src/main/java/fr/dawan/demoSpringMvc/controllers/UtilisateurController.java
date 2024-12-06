package fr.dawan.demoSpringMvc.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.dawan.demoSpringMvc.entities.Utilisateur;
import fr.dawan.demoSpringMvc.services.IUtilisateurService;

@Controller
@RequestMapping("utilisateurs")
public class UtilisateurController {
	
	@Autowired
	private IUtilisateurService utilisateurService;
	
	@GetMapping("/display")
	public String display(Model model) throws Exception {
		
		List<Utilisateur> users = utilisateurService.getAll();
		model.addAttribute("users", users);
		
		return "utilisateurs";
	}
	
	@PostMapping("/addUtilisateur")
	public String addUtilisateur(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("adminCheck") Boolean admin, @RequestParam("file") MultipartFile file, Model model) throws Exception {
		
		Utilisateur u = new Utilisateur(email, password, admin, file.getBytes());
		utilisateurService.saveOrUpdate(u);
		
		
		return "redirect:/utilisateurs/display";
	}

}
