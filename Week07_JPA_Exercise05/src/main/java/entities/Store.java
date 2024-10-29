package entities;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "stores")
public class Store {
	@Id
	@Column(name = "store_id", columnDefinition = "INT")
	private int storeId;
	
	@Column(name = "store_name", columnDefinition = "VARCHAR(255)")
	private String storeName;
	
	@OneToMany(mappedBy = "store")
	private List<Stock> stocks;
	
	@Embedded
	private Address address;
	
	private Contact contact;
	
	@OneToMany(mappedBy = "store")
	private List<Staff> staffs;
}
