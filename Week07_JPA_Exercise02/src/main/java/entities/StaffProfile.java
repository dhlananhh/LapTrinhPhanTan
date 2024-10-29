package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@ManyToOne
	@JoinColumn(name = "staff_id", unique = true, nullable = false)
	private Staff staff;
	
	
	@Id
	@ManyToOne
	@JoinColumn (name = "project_id", unique = true, nullable = false)
	private Project project;
	
	
	@Column(name = "avatar", columnDefinition = "VARCHAR(255)")
	private String avatar;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
}
