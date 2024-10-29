package streaming_readFile.entities;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Restaurant {
	private long restaurantID;
	private String name;
	private String borough;
	private String cuisine;
	private boolean isActive;
	private String[] categories;
	private Address address;
	private Grade[] grades;
	
	
	public Restaurant() {

	}


	public Restaurant (long restaurantID, String name, 
			String borough, String cuisine, boolean isActive,
			String[] categories, Address address, Grade[] grades) {
		this.restaurantID = restaurantID;
		this.name = name;
		this.borough = borough;
		this.cuisine = cuisine;
		this.isActive = isActive;
		this.categories = categories;
		this.address = address;
		this.grades = grades;
	}


	public long getRestaurantID() {
		return restaurantID;
	}


	public void setRestaurantID(long restaurantID) {
		this.restaurantID = restaurantID;
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


	public String[] getCategories() {
		return categories;
	}


	public void setCategories(String[] categories) {
		this.categories = categories;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public Grade[] getGrades() {
		return grades;
	}


	public void setGrades(Grade[] grades) {
		this.grades = grades;
	}


	@Override
	public int hashCode() {
		return Objects.hash(restaurantID);
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
		return restaurantID == other.restaurantID;
	}


	@Override
	public String toString() {
		return "Restaurant [restaurantID=" + restaurantID + ", name=" + name + ", borough=" + borough + ", cuisine="
				+ cuisine + ", isActive=" + isActive + ", categories=" + categories + ", address=" + address
				+ ", grades=" + grades + "]";
	}

}
