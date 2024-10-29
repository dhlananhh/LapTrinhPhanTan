package entities;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "categories")
public class Category {
	@Id
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "category_name", columnDefinition = "VARCHAR(255)")
	private String categoryName;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products;
}
