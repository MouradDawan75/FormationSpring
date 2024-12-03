package fr.dawan.training.entities;

import jakarta.persistence.Entity;

@Entity
public class Technicien extends Employe{
	
	private String poste;
	private int niveau;
	
	public String getPoste() {
		return poste;
	}
	public void setPoste(String poste) {
		this.poste = poste;
	}
	public int getNiveau() {
		return niveau;
	}
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	
	

}
