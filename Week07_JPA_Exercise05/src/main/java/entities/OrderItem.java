package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "order_items")
public class OrderItem {
	@Id
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "list_price")
	private double listPrice;
	
	@Column(name = "discount")
	private double discount;
}
