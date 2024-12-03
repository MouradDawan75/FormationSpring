package fr.dawan.demoSpringCore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Voiture {
	
	@Autowired
	private Moteur moteur;
	
	public void demarrer() {
		moteur.demarrer();
	}

}
