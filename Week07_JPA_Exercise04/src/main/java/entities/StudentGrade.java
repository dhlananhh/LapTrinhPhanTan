package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "StudentGrade")
public class StudentGrade {
	@Id
	@Column(name = "EnrollmentID", columnDefinition = "INT", unique = true, nullable = false)
	private int enrollmentID;
	
	@Column(name = "Grade", columnDefinition = "FLOAT")
	private double grade;
	
	@ManyToOne
	@JoinColumn(name = "StudentID")
	private Student	student;
	
	@ManyToOne
	@JoinColumn(name = "CourseID")
	private Course course;
}
