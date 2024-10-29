package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor


@Embeddable
public class Address {
	// Attributes
	@Column (name = "city", columnDefinition = "VARCHAR(255)")
	private String city;
	
	@Column (name = "state", columnDefinition = "VARCHAR(255)")
	private String state;
	
	@Column (name = "street", columnDefinition = "VARCHAR(255)")
	private String street;
	
	@Column (name = "zip_code", columnDefinition = "VARCHAR(255)")
	private String zipCode;

	
	// Constructor
	public Address (String city, String state, String street, String zipCode) {
		this.city = city;
		this.state = state;
		this.street = street;
		this.zipCode = zipCode;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format("Address [city = %s, state = %s, street = %s, zipCode = %s]", 
				city, state, street, zipCode);
	}
}
