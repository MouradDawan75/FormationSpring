package fr.dawan.demoSpringMvc.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.dawan.demoSpringMvc.entities.Utilisateur;
import fr.dawan.demoSpringMvc.repositories.UtilisateurRepository;

@Service
public class UtilisateurServiceImpl implements IUtilisateurService {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<Utilisateur> getAll() throws Exception{
		
		List<Utilisateur> lst = new ArrayList<>();
		
		for(Utilisateur u : utilisateurRepository.findAll()) {
			byte[] encodedBase64 = Base64.getEncoder().encode(u.getPhoto());
			String chaineBase64 = new String(encodedBase64,"UTF-8");
			u.setBase64Image(chaineBase64);
			lst.add(u);
		}
		
		return lst;
	}
	
	@Override
	public void saveOrUpdate(Utilisateur u) throws Exception{
		
		if(u.getId() == 0) {
			u.setPassword(encoder.encode(u.getPassword()));
		}else {
			Utilisateur userDB = utilisateurRepository.findById(u.getId()).get();
			
			if(!u.getPassword().equals(userDB.getPassword())) {
				u.setPassword(encoder.encode(u.getPassword()));
			}
		}
		
		utilisateurRepository.saveAndFlush(u);
		
	}
	
	@Override
	public void deteleById(int id) throws Exception{
		utilisateurRepository.deleteById(id);
	}
	
	@Override
	public Utilisateur findByEmail(String email) {
		Utilisateur user = utilisateurRepository.findByEmail(email);
		if(user != null) {
			return user;
		}
		return null;
	}
	
	@Override
	public Utilisateur getById(int id) throws Exception{
		Optional<Utilisateur> optional = utilisateurRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Utilisateur checkLogin(String email, String password) throws Exception{
		Utilisateur user = utilisateurRepository.findByEmail(email);
		if(user != null && encoder.matches(password, user.getPassword())) {
			
			byte[] encodedBase64 = Base64.getEncoder().encode(user.getPhoto());
			String chaineBase64 = new String(encodedBase64,"UTF-8");
			user.setBase64Image(chaineBase64);
			
			return user;
		}
		return null;
	}

}
