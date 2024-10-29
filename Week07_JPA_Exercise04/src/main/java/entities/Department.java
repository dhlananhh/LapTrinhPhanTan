package entities;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "Department")
public class Department {
	@Id
	@Column(name = "DepartmentID", columnDefinition = "INT", unique = true, nullable = false)
	private int departmentID;
	
	@Column(name = "Name", columnDefinition = "VARCHAR(255)")
	private String name;
	
	@Column(name = "Administrator", columnDefinition = "INT")
	private int administrator;
	
	@Column(name = "Budget", columnDefinition = "FLOAT")
	private double budget;
	
	@Column(name = "StartDate", columnDefinition = "DATETIME")
	private LocalDateTime startDate;
	
	@OneToMany(mappedBy = "department")
	private List<Course> courses;
}
