package exercise04.entities;

import java.time.LocalDate;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Customer {
	@BsonId
	private String customerId;
	@BsonProperty("first_name")
	private String firstName;
	@BsonProperty("last_name")
	private String lastName;
	@BsonProperty("email")
	private String emailName;
	@BsonProperty("address")
	private Address address;
	@BsonProperty("registrattion_date")
	private LocalDate registrationDate;
	@BsonProperty("phones")
	private List<Phone> phones;

	public Customer() {
	}

	/**
	 * 
	 * @param customerId
	 * @param firstName
	 * @param lastName
	 * @param emailName
	 * @param address
	 * @param registrationDate
	 * @param phones
	 */
	public Customer(String customerId, String firstName, String lastName, LocalDate registrationDate) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;		
		this.registrationDate = registrationDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailName=" + emailName + ", address=" + address + ", registrationDate=" + registrationDate
				+ ", phones=" + phones + "]";
	}
}
