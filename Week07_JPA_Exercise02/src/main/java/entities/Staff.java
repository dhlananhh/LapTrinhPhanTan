package entities;


import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "staffs")
public class Staff {
	@Id
	@Column(name = "staff_id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private long staffId;
	
	@Column(name = "age", columnDefinition = "INT")
	private int age;
	
	@Column(name = "staff_name", columnDefinition = "NVARCHAR(100)", nullable = false)
	private String staffName;
	
	@Column(name = "refers", columnDefinition = "VARCHAR(255)")
	private String references;
	
	@OneToOne(mappedBy = "staff")
	private StaffProfile profile;
		
	@ElementCollection
	@CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "staff_id"))
	@Column(name = "phone", nullable = false)
	private Set<String> phoneNumbers;

	@ManyToMany
	@JoinTable(name = "staffs_projects", 
		joinColumns = @JoinColumn(name = "staff_id"), 
		inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> projects;
	
//	@OneToMany (mappedBy = "staff")
//	private Set<Project> staff_project;
	
	@ManyToOne
	@JoinColumn(name = "dept_id")
	private Department department;
}
