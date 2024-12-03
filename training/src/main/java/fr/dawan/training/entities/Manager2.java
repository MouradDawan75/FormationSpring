package fr.dawan.training.entities;

import jakarta.persistence.Entity;

@Entity
public class Manager2 extends BaseEntity{
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
