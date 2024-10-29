package entities;

import java.util.Date;

public class Transaction {
	private String transactionId;
    private String transactionType;
    private double amount;
    private Date transactionDate;
    private SavingsAccount sourceAccount;
    private SavingsAccount destinationAccount;
}
