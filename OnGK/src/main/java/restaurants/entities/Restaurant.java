package restaurants.entities;


import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;


public class Restaurant {
	@BsonId
	private ObjectId id;
	
	@BsonProperty("restaurant_id")
	private String restaurantId;
	
	@BsonProperty("name")
	private String name;
	
	@BsonProperty("borough")
	private String borough;
	
	@BsonProperty("cuisine")
	private String cuisine;
	
	@BsonProperty("address")
	private Address address;
	
	@BsonProperty("grades")
	private List<Grade> grades;

	
	public Restaurant() {

	}


	public Restaurant (ObjectId id, String restaurantId, String name, String borough, 
			String cuisine, Address address, List<Grade> grades) {
		this.id = id;
		this.restaurantId = restaurantId;
		this.name = name;
		this.borough = borough;
		this.cuisine = cuisine;
		this.address = address;
		this.grades = grades;
	}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public String getRestaurantId() {
		return restaurantId;
	}


	public void setRestaurantId(String restaurantId) {
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


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public List<Grade> getGrades() {
		return grades;
	}


	public void setGrades(List<Grade> grades) {
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
		return Objects.equals(restaurantId, other.restaurantId);
	}


	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", restaurantId=" + restaurantId + ", name=" + name 
				+ ", borough=" + borough + ", cuisine=" + cuisine + ", address=" + address 
				+ ", grades=" + grades + "]";
	}
	
}
