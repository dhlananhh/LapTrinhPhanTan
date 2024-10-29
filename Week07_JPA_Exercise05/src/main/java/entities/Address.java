package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class Address {
	@Column(name = "street", columnDefinition = "VARCHAR(255)")
	private String street;
	
	@Column(name = "city", columnDefinition = "VARCHAR(255)")
	private String city;
	
	@Column(name = "state", columnDefinition = "VARCHAR(255)")
	private String state;
	
	@Column(name = "zip_code", columnDefinition = "INT")
	private int zipCode;
}
