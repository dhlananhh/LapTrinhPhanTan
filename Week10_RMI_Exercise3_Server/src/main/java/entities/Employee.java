package entities;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor


@Entity
@Table (name = "employees")
public class Employee {
	// Attributes
	@Id
	@Column (name = "employee_id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private long employeeID;
	
	@Column (name = "first_name", columnDefinition = "VARCHAR(255)")
	private String firstName;
	
	@Column (name = "last_name", columnDefinition = "VARCHAR(255)")
	private String lastName;
	
	@Column (name = "email", columnDefinition = "VARCHAR(255)")
	private String email;
	
	@Column (name = "phone_number", columnDefinition = "VARCHAR(255)")
	private String phoneNumber;

	// Relationships
	@OneToMany (mappedBy = "employee")
	private Set<EmployeeProject> employee_project;
	
	
	// Constructor
	public Employee (long employeeID, String firstName, String lastName, String email, String phoneNumber) {
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format("Employee [employeeID = %d, firstName = %s, lastName = %s, email = %s, phoneNumber = %s]", 
				employeeID, firstName, lastName, email, phoneNumber);
	}
}
