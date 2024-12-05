package fr.dawan.training.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDto {
	
	private int id;
	private String name;
	
	/* Si besoin de modifier le nom de l'attribut dans le DTO.
	 * On doit fournir dans @JsonProperty(value = "name") le nom de la propriété JSON à mapper
	@JsonProperty(value = "name")
	private String nom;
	*/
	private String address;
	private String zipCode;
	private String city;
	private String country;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	//Pour construire des propriétés spécifiques non contenues dans la réponse de l'api externe
	@JsonIgnore
	private String getAdresseComplete;
	
	public String getAdresseComplete() {
		return address+" "+zipCode+" "+city+" "+country;
	}

	
}
