package fr.dawan.demoSpringMvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.dawan.demoSpringMvc.entities.Produit;
import fr.dawan.demoSpringMvc.formbeans.ProduitForm;
import fr.dawan.demoSpringMvc.services.IProduitService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("produits")
public class ProduitController {
	
	@Autowired
	private IProduitService produitService;
	
	@GetMapping("/display")
	public String display(Model model, @RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "3") int size) throws Exception {
		
		displayPaging(model, page, size);
		
		return "produits";
	}

	private void displayPaging(Model model, int page, int size) {
		List<Produit> totalProduits = new ArrayList();
		
		String key = (String) model.getAttribute("key");
		
		if(key != null) {
			totalProduits = produitService.findByKey(key);
		}else{
			totalProduits = produitService.getAll();
		}
		
		
		
		int totalItem = totalProduits.size();
		int totalPages = 0;
		
		if(totalItem <= size) {
			totalPages = 1;
		}else {
			if(totalItem % size == 0) {
				totalPages = totalItem / size;
			}else {
				totalPages = totalItem / size + 1;
			}
		}
		
		
		
		if(key != null) {
			model.addAttribute("listProduits", produitService.getAllPagingByDescription(page, size, key));
		}else {
			model.addAttribute("listProduits", produitService.getAllPaging(page, size));
		}
		
		
		model.addAttribute("size", size);
		
		if(totalPages > 0) {
			//Construire une liste d'entiers
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
		ProduitForm produitForm = (ProduitForm) model.getAttribute("produitForm");
		if(produitForm != null) {
			model.addAttribute("produitForm", produitForm);
		}
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) throws Exception{
		produitService.delete(id);
		return "redirect:/produits/display";
		
	}
	
	@GetMapping("/modify/{id}")
	public String editer(@PathVariable("id") int id, Model model) throws Exception{
		Produit produit = produitService.getById(id);
		ProduitForm produitForm = new ProduitForm(produit.getId(), produit.getDescription(), produit.getPrix(), produit.getQuantite());
		model.addAttribute("produitForm", produitForm);
		displayPaging(model, 1, 3);
		
		return "produits";
		
	}
	
	@PostMapping("/addOrUpdate")
	public String addOrUpdate(@Valid @ModelAttribute ProduitForm produitForm, BindingResult bindingResult, Model model) throws Exception{
		//BindingResult contient les erreurs de validation si le ProduitForm n'est pas valide
		
		if(bindingResult.hasErrors()) {
			displayPaging(model, 1, 3);
			return "produits";
		}
		
		Produit p = new Produit(produitForm.getId(), produitForm.getDescription(), produitForm.getPrix(), produitForm.getQuantite());
		if(p.getId() == 0) {
			produitService.create(p);
		}else {
			produitService.update(p);
		}
		
		return "redirect:/produits/display";
	}
	
	@GetMapping("/findByKey")
	public String getByKey(@RequestParam("motCle") String key, Model model) throws Exception{
		model.addAttribute("key", key);
		displayPaging(model, 1, 3);
		
		return "produits";
	}
	
	@GetMapping("/moins/{id}")
	public String moins(@PathVariable("id") int id, Model model) throws Exception{
		Produit produit = produitService.getById(id);
		
		if(produit.getQuantite() > 1) {
			produit.setQuantite(produit.getQuantite() - 1);
			produitService.update(produit);
		}
		
		displayPaging(model, 1, 3);
		
		return "produits";
		
	}
	
	@GetMapping("/plus/{id}")
	public String plus(@PathVariable("id") int id, Model model) throws Exception{
		Produit produit = produitService.getById(id);
		
		if(produit.getQuantite() < 100) {
			produit.setQuantite(produit.getQuantite() + 1);
			produitService.update(produit);
		}
		
		displayPaging(model, 1, 3);
		
		return "produits";
		
	}
	
	@GetMapping("/download-csv")
	public void downloadCsv(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment;filename=produits.csv");
		
		ServletOutputStream out = response.getOutputStream();
		out.write("id,description,prix,quantite\n".getBytes());
		
		List<Produit> produits = produitService.getAll();
		for (Produit produit : produits) {
			out.write(produit.toCSV().getBytes());
		}
		
		out.close();
		
		
	}
	
	//Méthode permettant de connecter l'objet produitForm au formulaire de la page produits.html 
	
	@ModelAttribute("produitForm")
	public ProduitForm getProduiForm() {
		return new ProduitForm();
	}


}
