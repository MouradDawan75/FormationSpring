package fr.dawan.training.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Category extends BaseEntity{
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	@Version
//	private int version;
//	
	@Column(unique = true, nullable = false)
	private String name;
	
	/*
	 * Mode du chargement des relations: les relations Many sont en mode Lazy
	 * Mode Lazy: chargement tardif (chargement à la demande - faire appel au getter de products)
	 * Mode Eager: chargement immédiat
	 * 
	 * cascade.remove: la suppression d'une category implique la suppression de son id dans les différentes relations
	 */
	
	//@OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) changer le mode de chargement de la relation
	@OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE) //category_id est une clé de jointure: on gérer la cascade.remove
	private Set<Product> products = new HashSet();

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public int getVersion() {
//		return version;
//	}
//
//	public void setVersion(int version) {
//		this.version = version;
//	}

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
