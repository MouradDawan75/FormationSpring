package fr.dawan.training.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class Player2 extends BaseEntity{
	
	private String name;
	
	@OneToOne @MapsId
	private Manager2 manager;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Manager2 getManager() {
		return manager;
	}

	public void setManager(Manager2 manager) {
		this.manager = manager;
	}
	
	

}
