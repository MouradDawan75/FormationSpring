package fr.dawan.training.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Voiture extends Vehicule {
	
	private int nbPlaces;
	
	public int getNbPlaces() {
		return nbPlaces;
	}
	
	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}


	

}
