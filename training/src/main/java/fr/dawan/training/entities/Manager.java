package fr.dawan.training.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable //pas de table en BD -> pour chaque attribut une colonne est générée dans la table Player
public class Manager {

	@Column(name = "manager_name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
