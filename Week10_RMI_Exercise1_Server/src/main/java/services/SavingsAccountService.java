package services;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;

import dao.SavingsAccountDAO;
import entities.SavingsAccount;
import handler.Luhn;


public class SavingsAccountService extends UnicastRemoteObject implements SavingsAccountDAO  {
	private static final long serialVersionUID = 1L;
	private List<SavingsAccount> savingsAccounts;
	

	public SavingsAccountService() throws RemoteException {
		this.savingsAccounts = new ArrayList<SavingsAccount>();
	}

	@Override
	public boolean validateCardNumber(String cardNumber) throws RemoteException {
		return Luhn.validateCardNumber(cardNumber);
	}

	@Override
	public String generateCardNumber() throws RemoteException {
		return Luhn.generateCardNumber();
	}

	@Override
	public void createSavingsAccount (String cardNumber, String accountHolderName, double balance) throws RemoteException {
		if (!validateCardNumber(cardNumber)) {
            throw new IllegalArgumentException("Invalid card number");
        }

        SavingsAccount savingsAccount = new SavingsAccount(cardNumber, accountHolderName, balance);
        savingsAccounts.add(savingsAccount);
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccounts() throws RemoteException {
		return new ArrayList<>(savingsAccounts);
	}

	@Override
	public SavingsAccount getSavingsAccountByCardNumber(String cardNumber) throws RemoteException {
		return savingsAccounts.stream()
                .filter(savingsAccount -> savingsAccount.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElse(null);
	}

	@Override
	public void deposit(String cardNumber, double amount) throws RemoteException {
		SavingsAccount savingsAccount = getSavingsAccountByCardNumber(cardNumber);
        if (savingsAccount == null) {
            throw new IllegalArgumentException("Số tài khoản không tồn tại");
        }

        savingsAccount.setBalance(savingsAccount.getBalance() + amount);
	}

	@Override
	public void withdraw(String cardNumber, double amount) throws RemoteException {
		SavingsAccount savingsAccount = getSavingsAccountByCardNumber(cardNumber);
        if (savingsAccount == null) {
            throw new IllegalArgumentException("Invalid card number. Please check again!");
        }

        if (savingsAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance. Please refill your account!");
        }

        savingsAccount.setBalance(savingsAccount.getBalance() - amount);
	}
}
