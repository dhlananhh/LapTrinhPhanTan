package entities;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode


@Entity
@Table (name = "savings_account")
public class SavingsAccount implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@Column (name = "card_number", columnDefinition = "VARCHAR(16)")
	private String cardNumber;
	
	@Column (name = "account_holder_name", columnDefinition = "VARCHAR(255)")
    private String accountHolderName;
	
	@Column (name = "balance", columnDefinition = "FLOAT")
    private double balance;
	
	
	// Constructor
	public SavingsAccount(String cardNumber, String accountHolderName, double balance) {
		this.cardNumber = cardNumber;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
	}

	
	// toString
	@Override
	public String toString() {
		return String.format("SavingsAccount [cardNumber = %s, accountHolderName = %s, balance = %f]", 
				cardNumber, accountHolderName, balance);
	}
}
