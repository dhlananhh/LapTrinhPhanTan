package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter


@Embeddable
public class Address {
	@Column (name = "street", columnDefinition = "VARCHAR(255)")
	private String street;
	
	@Column (name = "city", columnDefinition = "VARCHAR(255)")
	private String city;
	
	@Column (name = "country", columnDefinition = "VARCHAR(255)")
	private String country;
	
	@Column (name = "zip_code", columnDefinition = "INT")
	private int zipCode;
	
	
	// Constructor
	public Address (String street, String city, String country, int zipCode) {
		this.street = street;
		this.city = city;
		this.country = country;
		this.zipCode = zipCode;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format("Address [street = %s, city = %s, country = %s, zipCode = %d]", 
				street, city, country, zipCode);
	}
}
