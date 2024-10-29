package entities;


import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "projects")
public class Project {
	@Id
	@Column(name = "project_id", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
	private String projectId;
	
	@Column(name = "project_name", columnDefinition = "VARCHAR(255)")
	private String projectName;
	
	@Column(name = "budget", columnDefinition = "FLOAT")
	private double budget;
	
	@ManyToMany(mappedBy = "projects")
	private List<Staff> staffs;
	
//	@OneToMany (mappedBy = "project")
//	private Set<Project> staff_project;
}
