package exercise01.entities;


import java.util.Objects;


public class Product {
	private String id;
	private String brandName;
	private String categoryName;
	private Enum color;
	private int modelYear;
	private String productName;
	private double price;
	
	
	public Product() {
		
	}


	public Product (String id, String brandName, String categoryName, 
			Enum color, int modelYear, String productName, double price) {
		this.id = id;
		this.brandName = brandName;
		this.categoryName = categoryName;
		this.color = color;
		this.modelYear = modelYear;
		this.productName = productName;
		this.price = price;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public Enum getColor() {
		return color;
	}


	public void setColor(Enum color) {
		this.color = color;
	}


	public int getModelYear() {
		return modelYear;
	}


	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", brandName=" + brandName + ", categoryName=" + categoryName + ", color=" + color
				+ ", modelYear=" + modelYear + ", productName=" + productName + ", price=" + price + "]";
	}
	
}
