package entities;


import lombok.Setter;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;


@Setter
@Getter


@Entity
@Table (name = "products")
public class Product {
	// Attributes
	@Id
	@Column (name = "product_id", columnDefinition = "VARCHAR(255)")
	private String productId;
	
	@Column (name = "description", columnDefinition = "VARCHAR(255)")
	private String description;
	
	@Column (name = "price", columnDefinition = "FLOAT")
	private double price;
	
	
	@ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn (name = "order_id")
	private List<OrderDetail> orderDetails;
}
