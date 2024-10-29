package entities;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "OfficeAssignment")
public class OfficeAssignment {
	@Id
	@OneToOne
	@JoinColumn(name = "InstructorID", unique = true, nullable = false)
	private Instructor instructor;
	
	@Column(name = "Location", columnDefinition = "VARCHAR(255)")
	private String location;
	
	@Column(name = "Timestamp", columnDefinition = "TIMESTAMP")
	private Timestamp timestamp;
}
