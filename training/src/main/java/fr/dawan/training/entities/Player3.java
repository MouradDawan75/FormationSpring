package fr.dawan.training.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Player3 extends BaseEntity{
	
	private String name;
	
	@OneToOne
	private Manager3 manager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manager3 getManager() {
		return manager;
	}

	public void setManager(Manager3 manager) {
		this.manager = manager;
	}

	
}
