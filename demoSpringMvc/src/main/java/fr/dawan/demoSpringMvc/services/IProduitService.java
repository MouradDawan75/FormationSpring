package fr.dawan.demoSpringMvc.services;

import java.util.List;

import fr.dawan.demoSpringMvc.entities.Produit;

public interface IProduitService {

	void create(Produit p);

	void update(Produit p);

	void delete(int id);

	List<Produit> getAll();

	Produit getById(int id);

	List<Produit> findByKey(String key);

	List<Produit> getAllPaging(int page, int size);

	List<Produit> getAllPagingByDescription(int page, int size, String key);

}