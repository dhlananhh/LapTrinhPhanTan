package exercise01.entities;


public class ShippingAddress {
	private String city;
	private String zipCode;
	private String street;
	private String state;
	
	
	public ShippingAddress() {
	
	}


	public ShippingAddress (String city, String zipCode, String street, String state) {
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
		this.state = state;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "ShippingAddress [city=" + city + ", zipCode=" + zipCode + ", street=" + street 
				+ ", state=" + state + "]";
	}
	
}
