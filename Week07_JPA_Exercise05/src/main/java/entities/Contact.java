package entities;


import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor


public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
}
