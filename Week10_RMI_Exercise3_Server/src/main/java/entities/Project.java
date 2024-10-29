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
@Table (name = "projects")
public class Project {
	// Attributes
	@Id
	@Column (name = "project_id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private long projectID;
	
	@Column (name = "project_name", columnDefinition = "VARCHAR(255)")
	private String projectName;
	
	@Column (name = "type", columnDefinition = "VARCHAR(255)")
	private String type;
	
	@Column (name = "versions", columnDefinition = "VARCHAR(255)")
	private Set<String> versions;

	
	// Relationships
	@OneToMany (mappedBy = "project")
	private Set<EmployeeProject> employee_project;
	
	// Constructor
	public Project (long projectID, String projectName, String type, Set<String> versions) {
		this.projectID = projectID;
		this.projectName = projectName;
		this.type = type;
		this.versions = versions;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format("Project [projectID = %d, projectName = %s, type = %s, versions = %s]", 
			projectID, projectName, type, versions);
	}
}
