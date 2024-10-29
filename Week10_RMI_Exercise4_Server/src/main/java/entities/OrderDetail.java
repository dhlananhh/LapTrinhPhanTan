package entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor


@Entity
@Table (name = "order_detail")
public class OrderDetail {
	@Id
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "order_id")
	private Order order;
	
	@Id
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "product_id")
	private Product product;
	
	@Column (name = "quantity", columnDefinition = "INT", nullable = false)
	private int quantity;

	@Override
	public String toString() {
		return String.format("OrderDetail [orderId = %d, productId = %s, quantity = %d]", 
				order.getOrderId(), product.getProductId(), quantity);
	}
	
	
	
}
