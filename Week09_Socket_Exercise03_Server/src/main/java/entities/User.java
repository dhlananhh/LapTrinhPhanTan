package entities;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode


@Entity
@Table(name = "user")


// JPQL
@NamedQueries({
	@NamedQuery(name = "User.listAllUsers", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findUserByEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail"),
	@NamedQuery(name = "User.findUserById", query = "SELECT u FROM User u WHERE u.userId = :userId"),
	@NamedQuery(name = "User.findUserByName", query = "SELECT u FROM User u WHERE u.userName = :userName")
	
})


public class User {
	// Attributes
	@Id
	@Column(name = "user_id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private long userId;
	
	@Column(name = "research_areas", columnDefinition = "VARCHAR(255)")
	private Set<String> researchAreas;

	@Column(name = "user_email", columnDefinition = "VARCHAR(255)")
	private String userEmail;
	
	@Column(name = "user_name", columnDefinition = "VARCHAR(255)")
	private String userName;
	
	@Column(name = "user_password", columnDefinition = "VARCHAR(255)")
	private String userPassword;
	
	@Embedded
	private Address address;
	
	@OneToMany (mappedBy = "user")
	private Set<Comments> comments;
	
	
	// Constructor
	public User (long userId, Set<String> researchAreas, String userEmail, 
			String userName, String userPassword, Address address) {
		this.userId = userId;
		this.researchAreas = researchAreas;
		this.userEmail = userEmail;
		this.userName = userName;
		this.userPassword = userPassword;
		this.address = address;
	}
	
	
	// toString
	@Override
	public String toString() {
		return String.format("User [userId = %s, researchAreas = %s, userEmail = %s, userName = %s, userPassword = %s]",
				userId, researchAreas, userEmail, userName, userPassword);
	}
}
