package entities;


import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)


@Entity
@Table(name = "customers")
public class Customer extends Person {
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;
}
