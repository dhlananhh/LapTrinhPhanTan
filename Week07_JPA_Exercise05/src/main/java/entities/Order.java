package entities;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "order_status")
	private Byte orderStatus;
	
	@Column(name = "order_date")
	private LocalDate orderDate;
	
	@Column(name = "required_date")
	private LocalDate requiredDate;
	
	@Column(name = "shipped_date")
	private LocalDate shippedDate;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;
}
