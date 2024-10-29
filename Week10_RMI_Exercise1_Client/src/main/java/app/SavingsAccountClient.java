package app;


import services.SavingsAccountService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class SavingsAccountClient {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		String host = "localhost";
        int port = 1099;

        SavingsAccountService service = (SavingsAccountService) Naming.lookup("//" + host + ":" + port + "/SavingsAccountService");

        String cardNumber = "49927398716";
        boolean isValid = service.validateCardNumber(cardNumber);
        System.out.println("Account number: " + cardNumber + " Validity: " + isValid);

        String generatedCardNumber = service.generateCardNumber();
        System.out.println("Account number is generated: " + generatedCardNumber);
	}

}
