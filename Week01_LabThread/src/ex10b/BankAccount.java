package ex10b;

public class BankAccount {
	private String accountID;
	private String accountName;
	private double balance;
	
	
	public BankAccount (String accountID, String accountName) {
		this.accountID = accountID;
		this.accountName = accountName;
		this.balance = 100.0;
	}
	
	
	public String getAccountID() {
		return accountID;
	}


	public String getAccountName() {
		return accountName;
	}

	
	public double getBalance() {
		return balance;
	}
	
	
	/**
		Deposits money into the bank account.
		Chuyển tiền
		@param amount the amount to deposit
	**/
	public synchronized void deposit (double amount) {
		if (amount > 0) {
			balance += amount;
			notifyAll();
			System.out.printf("%s deposit %f, balance %f\n", Thread.currentThread().getName(), amount, balance);
		}
	}
	
	
	/**
		Withdraws money from the bank account
		Rút tiền
		@param amount the amount to withdraw
	 	@throws InterruptedException 
	**/
	public synchronized double withdraw (double amount) throws InterruptedException {
		while (amount > balance) {
			System.out.printf("%s Not enough money. Waiting ...\n", Thread.currentThread().getName());
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		balance -= amount;
		System.out.printf("%s withdraw %f, balance %f\n", Thread.currentThread().getName(), amount, balance);
		
		return amount;
	}	
}
