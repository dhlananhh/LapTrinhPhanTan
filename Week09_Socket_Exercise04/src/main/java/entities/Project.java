package entities;


import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "Project")
public class Project {
	@Id
	@Column(name = "projectID", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
	private String projectID;
	
	@Column(name = "projectName", columnDefinition = "VARCHAR(255)")
	private String projectName;
	
	@Column(name = "type", columnDefinition = "VARCHAR(100)")
	private String type;
	
	@Column(name = "versions", columnDefinition = "VARCHAR(255)")
	private Set<String> versions;
	
	@ManyToMany(mappedBy = "projects")
	private List<Employee> employees;
}
