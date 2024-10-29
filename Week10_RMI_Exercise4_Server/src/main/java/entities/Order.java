package entities;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode


@Entity
@Table (name = "orders")
public class Order {
	// Attributes
	@Id
	@Column (name = "order_id", columnDefinition = "INT")
	private int orderId;
	
	@Column (name = "order_date", columnDefinition = "DATETIME")
	private Date orderDate;
	
	@OneToMany (mappedBy = "order")
	private List<OrderDetail> orderDetails;
	
	
	// Constructor
	public Order (int orderId, Date orderDate) {
		this.orderId = orderId;
		this.orderDate = orderDate;
	}


	// toString
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + "]";
	}
	
	
	
}
