package exercise01.entities;


import java.util.Objects;


public class Staff {
	private String id;
	private String firstName;
	private String lastName;
	private Phone phone;
	private int managerId;
	private String email;
	
	
	public Staff() {

	}

	
	public Staff (String id) {
		this.id = id;
		getFullName();
		getPhoneNumber();
	}
	
	
	public Staff (String id, String firstName, String lastName, Phone phone, int managerId, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.managerId = managerId;
		this.email = email;
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


	public Phone getPhone() {
		return phone;
	}


	public void setPhone(Phone phone) {
		this.phone = phone;
	}


	public int getManagerId() {
		return managerId;
	}


	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	private String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	
	private String getPhoneNumber() {
		return this.phone.getNumber();
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
		Staff other = (Staff) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Staff [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", managerId=" + managerId + ", email=" + email + "]";
	}
	
}
