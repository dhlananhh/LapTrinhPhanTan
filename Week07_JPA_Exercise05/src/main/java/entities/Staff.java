package entities;


import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "staffs")
@AttributeOverride(name = "id", column = @Column(name = "staff_id"))
public class Staff extends Person {
	@Column(name = "active", columnDefinition = "BYTE")
	private Byte active;

	@OneToMany(mappedBy = "manager")
	private List<Staff> staffs;
	
	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Staff manager;
	
	@OneToMany(mappedBy = "staff")
	private List<Order> orders;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;
}
