package fr.dawan.training.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class Player extends BaseEntity{
	
	private String name;
	
	@Embedded
	private Manager manager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	

}
