package entities;


import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "Employee")
public class Employee {
	@Id
	@Column(name = "employeeID", columnDefinition = "BIGINT", unique = true, nullable = false)
	private long employeeID;
	
	@Column(name = "firstName", columnDefinition = "NVARCHAR(100)")
	private String firstName;
	
	@Column(name = "lastName", columnDefinition = "NVARCHAR(100)")
	private String lastName;
	
	@Column(name = "email", columnDefinition = "NVARCHAR(100)")
	private String email;
	
	@Column(name = "phoneNumber", columnDefinition = "VARCHAR(20)")
	private String phoneNumber;
	
	@ManyToMany
	@JoinTable(name = "EmployeeProject", 
		joinColumns = @JoinColumn(name = "employeeID"), 
		inverseJoinColumns = @JoinColumn(name = "projectID"))
	private List<Project> projects;
}
