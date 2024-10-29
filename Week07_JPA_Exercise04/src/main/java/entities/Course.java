package entities;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Course")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Course {
	@Id
	@Column(name = "CourseID", columnDefinition = "INT", unique = true, nullable = false)
	protected int id;
	
	@Column(name = "Title", columnDefinition = "VARCHAR(255)")
	protected String title;
	
	@Column(name = "Credits", columnDefinition = "INT")
	protected int credits;
	
	@ManyToOne
	@JoinColumn(name = "DepartmentID")
	private Department department;
	
	@OneToMany(mappedBy = "course")
	private List<StudentGrade> studentGrades;
	
	@ManyToMany
	@JoinTable(name = "CourseInstructor",
        joinColumns = @JoinColumn(name = "CourseID"),
        inverseJoinColumns = @JoinColumn(name = "InstructorID"))
	private List<Instructor> instructors;
}
