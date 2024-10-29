package entities;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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


@Entity(name = "Instructor")
@AttributeOverride(name = "id", column = @Column(name = "InstructorID"))
public class Instructor extends Person {
	private LocalDateTime hireDate;
	
	@ManyToMany(mappedBy = "instructors")
	private List<Course> courses;
}
