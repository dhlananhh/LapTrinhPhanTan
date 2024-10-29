package exercise03.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id", columnDefinition = "BIGINT", nullable = false)
	private int id;
	
	@Column(name = "first_name", columnDefinition = "VARCHAR(255)", nullable = true)
	private String firstName;
	
	@Column(name = "last_name", columnDefinition = "VARCHAR(255)", nullable = true)
	private String lastName;
	
	@Column(name = "ssn", columnDefinition = "VARCHAR(255)", nullable = true)
	private String socialSecurityNumber;
}
