package entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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


@Entity(name = "Student")
@AttributeOverride(name = "id", column = @Column(name = "StudentID"))
public class Student extends Person {
	private LocalDateTime enrollmentDate;
	
	@OneToMany(mappedBy = "student")
	private List<StudentGrade> studentGrades;
}
