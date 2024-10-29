package exercise02.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "profiles")
public class StaffProfile {
	@Id
	@OneToOne
	@JoinColumn(name = "staff_id")
	private long id;
	
	@Column(name = "avatar", columnDefinition = "VARCHAR(255)", nullable = true)
	private String avatar;
	
	@Column(name = "description", columnDefinition = "TEXT", nullable = true)
	private String description;
	
	@OneToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "staff_id", unique = true, nullable = false)
	private Staff staff;
}
