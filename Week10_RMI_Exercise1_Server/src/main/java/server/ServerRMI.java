package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dao.SavingsAccountDAO;
import services.SavingsAccountService;

public class ServerRMI {
	public static void main(String[] args) throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(9999);
		
		SavingsAccountDAO service = new SavingsAccountService();
		
		registry.rebind("SavingsAccountService", service);
		
		System.out.println("Server is running...");
	}
}
