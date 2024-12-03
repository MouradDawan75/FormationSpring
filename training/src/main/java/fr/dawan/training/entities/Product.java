package fr.dawan.training.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import jakarta.persistence.*;
/*
 * Gestion de l'accès conccurrentiel à une ressource partagée
 * Le contrôle de version des entités dans Spring Data permet aux dév. de gérer efficacement les modifications apportés
 * a leur entité.
 * 
 * Avantages:
 * - permet d'éviter la perte de mise à jours en garantissant qu'une entité n'est pas mise à jours par plusieurs
 * threads au mm temps
 * - offre un suivi des modifications: permettant en cas d'erreurs de revenir à l'état précedent
 * 
 * 
 * 
 */


@Entity
public class Product extends BaseEntity{

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	
//	@Version
//	private int version;
	
	@Column(unique = true, nullable=false , length = 512)
	private String description;
	
	@Column(name = "prixht", precision = 2)
	private double price;
	
	//private byte[] imagePath; -- pour sauvegarder le fichier binaire en BD
	private String imagePath; // pour sauvegarder l'emplacement du fichier en BD
	
	public enum ProductState{
		NEUF, EXCELLENT, MAUVAIS
	}
	
	@Enumerated(EnumType.STRING)
	private ProductState state;
	
	@Column(columnDefinition = "TEXT")
	private String comment;
	
	@ElementCollection
	private Set<String> ingredients = new HashSet(); // Set est une liste sans doublons - 1 table product_ingredient (pk - fk: product_id)
	
	@ElementCollection
	@CollectionTable(name = "t_prices_promo")
	@MapKeyColumn(name="promotion")
	@Column(name = "price")
	private Map<String, Double> pricesByPromotions = new HashMap();
	
	@Transient //ce champs sera ignoré lors du mapping
	private String champIgnore;
	
	//ManyToOne avec Category
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	//ManyToMany avec Supplier
	
	//product-id est une clé de jointure -> gestion de cascade.remove
	@ManyToMany(mappedBy = "products", cascade = CascadeType.REMOVE)
	private Set<Supplier> suppliers = new HashSet();
	
	
	public Set<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Set<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ProductState getState() {
		return state;
	}

	public void setState(ProductState state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<String> ingredients) {
		this.ingredients = ingredients;
	}

	public Map<String, Double> getPricesByPromotions() {
		return pricesByPromotions;
	}

	public void setPricesByPromotions(Map<String, Double> pricesByPromotions) {
		this.pricesByPromotions = pricesByPromotions;
	}

	public String getChampIgnore() {
		return champIgnore;
	}

	public void setChampIgnore(String champIgnore) {
		this.champIgnore = champIgnore;
	}

//	public int getVersion() {
//		return version;
//	}
//
//	public void setVersion(int version) {
//		this.version = version;
//	}

	
	
}
