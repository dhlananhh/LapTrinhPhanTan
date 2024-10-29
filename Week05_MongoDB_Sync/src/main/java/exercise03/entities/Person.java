package exercise03.entities;


import java.util.List;


public class Person {
	private String firstName;
	private String lastName;
	private int age;
	private Address address;
	private List<PhoneNumber> phoneNumbers;
	
	
	public Person() {

	}
	

	public Person (String firstName, String lastName, int age, Address address, List<PhoneNumber> phoneNumbers) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.address = address;
		this.phoneNumbers = phoneNumbers;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@Override
	public String toString() {
		return String.format("Person [firstName=%s, lastName=%s, age=%s, address=%s, phoneNumbers=%s]", 
				firstName, lastName, age, address, phoneNumbers);
	}
	
}
