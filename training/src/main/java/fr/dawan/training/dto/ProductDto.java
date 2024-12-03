package fr.dawan.training.dto;

public class ProductDto {
	
	private int id;
	private int version;
	private String description;
	private double price;
	private int categoryId;
	private String imagePath;
	
	public ProductDto(int id, int version, String description, double price, int categoryId, String imagePath) {
		super();
		this.id = id;
		this.version = version;
		this.description = description;
		this.price = price;
		this.categoryId = categoryId;
		this.imagePath = imagePath;
	}
	public ProductDto() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
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
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	

}
