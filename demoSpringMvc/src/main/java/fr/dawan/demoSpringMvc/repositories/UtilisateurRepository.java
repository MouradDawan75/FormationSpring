package fr.dawan.demoSpringMvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.dawan.demoSpringMvc.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>{
	
	Utilisateur findByEmail(String email);

}
