package fr.dawan.training.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Supplier extends BaseEntity{
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@ManyToMany(cascade = CascadeType.REMOVE)
	private Set<Product> products = new HashSet();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	

}
