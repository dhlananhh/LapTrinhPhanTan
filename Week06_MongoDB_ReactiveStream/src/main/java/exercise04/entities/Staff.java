package exercise04.entities;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Staff {
	@BsonId
	private long staffId;
	
	@BsonProperty("first_name")
	private String firstName;
	
	@BsonProperty("last_name")
	private String lastName;
	
	@BsonProperty("phone")
	private Phone phone;
	
	@BsonProperty("manager_id")
	private Staff manager;

	@BsonProperty("email")
	private String email;
	
	
	public Staff() {
		super();
	}
	
	
	public Staff (long staffId, String firstName, String lastName, Phone phone, Staff manager, String email) {
		super();
		this.staffId = staffId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.manager = manager;
		this.email = email;
	}
	
	public long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
		this.staffId = staffId;
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
	
	public Staff getManager() {
		return manager;
	}
	public void setManager(Staff manager) {
		this.manager = manager;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", firstName=" + firstName + ", lastName=" + lastName + ", phone="
				+ phone + ", manager=" + manager + ", email=" + email + "]";
	}
	
}
