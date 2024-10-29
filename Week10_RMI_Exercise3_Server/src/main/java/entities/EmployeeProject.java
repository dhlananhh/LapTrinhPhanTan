package entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "employees_projects")
public class EmployeeProject {
	@Column (name = "houres", columnDefinition = "INT")
	private int hours;
	
	
	@Id
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "employee_id")
	private Employee employee;
	
	
	@Id
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "project_id")
	private Project project;
	
	
	// Constructor
	public EmployeeProject (int hours) {
		this.hours = hours;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format("EmployeeProject [hours = %s]", hours);
	}
}
