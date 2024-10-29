package entities;


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
@Table(name = "products")
public class Product {
	@Id
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "product_name", columnDefinition = "VARCHAR(255)")
	private String productName;
	
	@Column(name = "model_year", columnDefinition = "INT")
	private int modelYear;
	
	@Column(name = "list_price", columnDefinition = "FLOAT")
	private double listPrice;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<Stock> stocks;
	
	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems;
}
