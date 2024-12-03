package fr.dawan.training.dto;

import java.util.HashSet;
import java.util.Set;

public class SupplierDto {
	
	private int id;
	private String name;
	private int version;
	private Set<Integer> productsIds = new HashSet();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Set<Integer> getProductsIds() {
		return productsIds;
	}
	public void setProductsIds(Set<Integer> productsIds) {
		this.productsIds = productsIds;
	}
	
	

}
