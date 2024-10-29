package exercise02.entities;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dept_id", columnDefinition = "VARCHAR(50)", nullable = false)
	private String departmentId;
	
	@Column(name = "location", columnDefinition = "VARCHAR(255)", nullable = true)
	private String location;
	
	@Column(name = "dept_name", columnDefinition = "VARCHAR(255)", nullable = true)
	private String departmentName;
	
	@OneToMany(mappedBy = "department")
	@ToString.Exclude
	private List<Staff> staffs;
}
