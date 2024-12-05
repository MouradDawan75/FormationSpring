package fr.dawan.demoSpringMvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.dawan.demoSpringMvc.entities.Produit;
import fr.dawan.demoSpringMvc.repositories.ProduitRepository;

@Service
public class ProduitServiceImpl implements IProduitService {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Override
	public void create(Produit p) {
		produitRepository.saveAndFlush(p);
	}
	
	@Override
	public void update(Produit p) {
		produitRepository.saveAndFlush(p);
	}
	
	@Override
	public void delete(int id) {
		produitRepository.deleteById(id);
	}
	
	@Override
	public List<Produit> getAll(){
		return produitRepository.findAll();
	}
	
	@Override
	public Produit getById(int id) {
		return produitRepository.findById(id).get();
	}
	
	@Override
	public List<Produit> findByKey(String key){
		return produitRepository.findByDescriptionContaining(key);
	}
	
	@Override
	public List<Produit> getAllPaging(int page, int size){
		return produitRepository.findAll(PageRequest.of(page - 1, size)).getContent();
	}
	
	@Override
	public List<Produit> getAllPagingByDescription(int page, int size, String key){
		return produitRepository.findByDescriptionContaining(key, PageRequest.of(page - 1, size)).getContent();
	}

}
