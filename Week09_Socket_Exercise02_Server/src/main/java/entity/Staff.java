package entity;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString


@Entity
@Table (name = "Staff")
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "StaffID", columnDefinition = "BIGINT")
	private Long id;
	
	@Column (name = "FirstName", columnDefinition = "NVARCHAR(255)")
	private String firstName;
	
	@Column (name = "LastName", columnDefinition = "NVARCHAR(255)")
	private String lastName;
	
	@Column (name = "Email", columnDefinition = "NVARCHAR(255)")
	private String email;
	
	@Column (name = "Phone", columnDefinition = "NVARCHAR(255)")
	private String phone;
	
	@Column (name = "Active", columnDefinition = "INT")
	private int active;
	
	
	public Staff (String firstName, String email) {
		this.firstName = firstName;
        this.email = email;
	}
}
