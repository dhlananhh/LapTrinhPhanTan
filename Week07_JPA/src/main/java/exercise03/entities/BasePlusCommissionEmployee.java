package exercise03.entities;


import jakarta.persistence.Entity;
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


@Entity
@Table(name = "base_plus_commission_employees")
public class BasePlusCommissionEmployee extends CommissionEmployee {
	private double baseSalary;
}
