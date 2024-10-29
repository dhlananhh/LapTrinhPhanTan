package de02_ObjModel.entities;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Restaurant {
	private long restaurantId;
	private String name;
	private String borough;
	private String cuisine;
	private boolean isActive;
	private List<String> categories;
	private Address address;
	private List<Grades> grades;
	
	
	public Restaurant() {

	}


	public Restaurant (long restaurantId, String name, 
			String borough, String cuisine, boolean isActive,
			List<String> categories, Address address, List<Grades> grades) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.borough = borough;
		this.cuisine = cuisine;
		this.isActive = isActive;
		this.categories = categories;
		this.address = address;
		this.grades = grades;
	}


	public long getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBorough() {
		return borough;
	}


	public void setBorough(String borough) {
		this.borough = borough;
	}


	public String getCuisine() {
		return cuisine;
	}


	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public List<String> getCategories() {
		return categories;
	}


	public void setCategories(List<String> categories) {
		this.categories = categories;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public List<Grades> getGrades() {
		return grades;
	}


	public void setGrades(List<Grades> grades) {
		this.grades = grades;
	}


	@Override
	public int hashCode() {
		return Objects.hash(restaurantId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		return restaurantId == other.restaurantId;
	}


	@Override
	public String toString() {
		return "Restaurant [restaurantId=" + restaurantId + ", name=" + name + ", borough=" + borough + ", cuisine="
				+ cuisine + ", isActive=" + isActive + ", categories=" + categories + ", address=" + address
				+ ", grades=" + grades + "]";
	}

}
