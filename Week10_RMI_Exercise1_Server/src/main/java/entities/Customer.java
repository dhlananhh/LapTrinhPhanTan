package entities;


import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode


@Entity
@Table (name = "customers")
public class Customer {
	@Id
	@Column (name = "customer_id", columnDefinition = "VARCHAR(255)")
	private String customerId;
	
	@Column (name = "customer_name", columnDefinition = "VARCHAR(255)")
	private String customerName;
	
	@Embedded
	private Address address;
	
	@Column (name = "email", columnDefinition = "VARCHAR(255)")
	private String email;
	
	@ElementCollection
	@CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "customer_id"))
	@Column(name = "phone_number", nullable = false)
	private Set<String> phoneNumbers;
	
	
	// Constructor
	public Customer (String customerId, String customerName, Address address, String email, Set<String> phoneNumbers) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
		this.email = email;
		this.phoneNumbers = phoneNumbers;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format(
			"Customer [customerId = %s, customerName = %s, address = %s, email = %s, phoneNumbers = %s]",
			customerId, customerName, address, email, phoneNumbers
		);
	}
}
