package entities;


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
@Table(name = "departments")
public class Department {
	@Id
	@Column(name = "dept_id", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
	private String departmentId;
	
	@Column(name = "dept_name", columnDefinition = "VARCHAR(255)")
	private String departmentName;
	
	@Column(name = "location", columnDefinition = "VARCHAR(255)")
	private String location;

	@OneToMany(mappedBy = "department")
	private List<Staff> staffs;
}
