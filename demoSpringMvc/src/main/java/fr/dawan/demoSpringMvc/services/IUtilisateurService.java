package fr.dawan.demoSpringMvc.services;

import java.util.List;

import fr.dawan.demoSpringMvc.entities.Utilisateur;

public interface IUtilisateurService {

	List<Utilisateur> getAll() throws Exception;

	void saveOrUpdate(Utilisateur u) throws Exception;

	void deteleById(int id) throws Exception;

	Utilisateur findByEmail(String email);

	Utilisateur getById(int id) throws Exception;

	Utilisateur checkLogin(String email, String password) throws Exception;

}