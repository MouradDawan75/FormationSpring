package fr.dawan.demoSpringMvc.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

@Entity
public class Utilisateur implements Serializable{
	
	/*
	 * LOB: Large Object
	 * CLOB: Character Large Object - contenu d'un livre
	 * BLOB: Big Large Object
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private boolean admin;
	
	@Lob
	@Column(name = "photo", columnDefinition = "MEDIUMBLOB")
	private byte[] photo;
	
	// Pour afficher une image dans page html, elle doit être convertie en chaine codée en 64 bits
	@Transient
	private String base64Image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public Utilisateur(int id, String email, String password, boolean admin, byte[] photo) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.photo = photo;
	}

	public Utilisateur(String email, String password, boolean admin, byte[] photo) {
		super();
		this.email = email;
		this.password = password;
		this.admin = admin;
		this.photo = photo;
	}

	public Utilisateur() {
		super();
	}
	
	

}
