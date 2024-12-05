package fr.dawan.demoSpringMvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.dawan.demoSpringMvc.entities.Produit;
import fr.dawan.demoSpringMvc.services.IProduitService;

class Player{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player(String name) {
		super();
		this.name = name;
	}

	public Player() {
		super();
	}
	
	
}

@Controller
public class HomeController {
	
	@Autowired
	private IProduitService produitService;
	
	@GetMapping({"","/"})
	public String accueil(Model model) throws Exception {
		
		model.addAttribute("message", "Page d'accueil");
		Player player = new Player("Léo");
		
		model.addAttribute("player", player);
		
		List<Player> players = new ArrayList();
		players.add(new Player("Jean"));
		players.add(new Player("Marie"));
		
		model.addAttribute("players", players);
		
		//throw new Exception();
		return "index";
	}
	
	@GetMapping("/load")
	public String loadTestData() throws Exception{
		
		//Pour charger des données de test une seule fois
		
		if(produitService.getAll().size() == 0) {
			produitService.create(new Produit("PC Dell", 1500, 10));
			produitService.create(new Produit("Ecran HP", 99, 7));
			produitService.create(new Produit("Photocopieur", 1250, 3));
			produitService.create(new Produit("Ecran Thomson", 75, 9));
		}
		
		
		
		return "redirect:/"; //redirection vers la page index
		
	}

}
