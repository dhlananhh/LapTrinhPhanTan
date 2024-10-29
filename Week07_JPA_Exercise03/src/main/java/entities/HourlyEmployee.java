package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "hourly_employees")
public class HourlyEmployee extends Employee {
	private double hours;
	private double wage;
}
