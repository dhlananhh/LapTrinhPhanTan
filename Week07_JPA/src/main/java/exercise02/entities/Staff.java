package exercise02.entities;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staff_id", columnDefinition = "BIGINT", nullable = false)
	private long staffId;
	
	@Column(name = "age", columnDefinition = "INT", nullable = true)
	private int age;
	
	@Column(name = "staff_name", columnDefinition = "NVARCHAR(100)", nullable = false)
	private String staffName;
	
	@Column(name = "refers", columnDefinition = "VARCHAR(255)", nullable = true)
	private String references;
	
	@OneToOne(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private StaffProfile profile;
		
	@ElementCollection
	@CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "staff_id"))
	@Column(name = "phone", nullable = false)
	private Set<String> phoneNumbers;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id", referencedColumnName = "dept_id")
	private Department department;
	
	@ManyToMany(mappedBy = "staffs", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Project> projects = new HashSet<Project>();
}
