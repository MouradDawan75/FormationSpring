package fr.dawan.training.entities;

import jakarta.persistence.Entity;

@Entity
public class Ingenieur extends Employe{
	
	private int nbProjets;
	private String status;
	
	public int getNbProjets() {
		return nbProjets;
	}
	public void setNbProjets(int nbProjets) {
		this.nbProjets = nbProjets;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
