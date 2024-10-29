package exercise02.entities;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "projects")
public class Project {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id", columnDefinition = "VARCHAR(50)", nullable = false)
	private String projectId;
	
	@Column(name = "project_name", columnDefinition = "VARCHAR(255)", nullable = true)
	private String projectName;
	
	@Column(name = "budget", columnDefinition = "DOUBLE", nullable = true)
	private double budget;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "staffs_projects", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"), inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "staff_id"))
	private Set<Staff> staffs = new HashSet<Staff>();
}
