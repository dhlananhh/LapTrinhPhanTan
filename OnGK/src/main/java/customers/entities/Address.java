package customers.entities;


import org.bson.codecs.pojo.annotations.BsonProperty;


public class Address {
	@BsonProperty("city")
	private String city;
	
	@BsonProperty("state")
	private String state;
	
	@BsonProperty("zip_code")
	private String zipCode;
	
	
	public Address() {
		
	}


	public Address (String city, String state, String zipCode) {
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	@Override
	public String toString() {
		return "Address [city=" + city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
	
}
