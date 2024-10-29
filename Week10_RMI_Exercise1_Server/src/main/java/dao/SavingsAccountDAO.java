package dao;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.SavingsAccount;


public interface SavingsAccountDAO extends Remote {
    public boolean validateCardNumber (String cardNumber) throws RemoteException;
    public String generateCardNumber() throws RemoteException;
    public void createSavingsAccount(String cardNumber, String accountHolderName, double balance) throws RemoteException;
    public List<SavingsAccount> getAllSavingsAccounts() throws RemoteException;
    public SavingsAccount getSavingsAccountByCardNumber(String cardNumber) throws RemoteException;
    public void deposit(String cardNumber, double amount) throws RemoteException;
    public void withdraw(String cardNumber, double amount) throws RemoteException;
}