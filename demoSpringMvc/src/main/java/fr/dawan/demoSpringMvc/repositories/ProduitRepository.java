package fr.dawan.demoSpringMvc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.demoSpringMvc.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer>{

	List<Produit> findByDescriptionContaining(String description);
	Page<Produit> findByDescriptionContaining(String desciption, Pageable page);
}
