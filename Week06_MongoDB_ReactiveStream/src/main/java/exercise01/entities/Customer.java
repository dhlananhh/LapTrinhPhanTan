package exercise01.entities;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Customer {
	@BsonId
	private String id;
	
	@BsonProperty("first_name")
	private String firstName;
	
	@BsonProperty("last_name")
	private String lastName;
	
	private Address address;
	
	@BsonProperty("registration_date")
	private LocalDateTime registrationDate;
	
	private String email;

	private List<Phone> phones;
	
	
	public Customer() {
		
	}
	
	
	public Customer (String id) {
		this.id = id;
	}


	public Customer (String id, String firstName, String lastName, 
			Address address, LocalDateTime registrationDate,
			String email, List<Phone> phones) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.registrationDate = registrationDate;
		this.email = email;
		this.phones = phones;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}


	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Phone> getPhones() {
		return phones;
	}


	public void setPhones(List<Phone> phones) {
		this.phones = phones;
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
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", registrationDate=" + registrationDate + ", email=" + email + ", phones=" + phones + "]";
	}
	
}
