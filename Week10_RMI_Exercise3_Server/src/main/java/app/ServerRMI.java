package app;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dao.EmployeeProjectDAO;
import dao.ProjectDAO;
import jakarta.persistence.EntityManager;
import services.EmployeeProjectService;
import services.EntityManagerFactoryUtil;
import services.ProjectService;



public class ServerRMI {
	public static void main (String[] args) throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(3003);
		
		EntityManagerFactoryUtil entityManagerFactory = new EntityManagerFactoryUtil();
		EntityManager entityManager = entityManagerFactory.getEntityManager();
				
		ProjectDAO projectDAO = new ProjectService(entityManager);
		EmployeeProjectDAO employeeProjectDAO = new EmployeeProjectService(entityManager);
		
		registry.bind("projectDAO", projectDAO);
		registry.bind("employeeProjectDAO", employeeProjectDAO);
				
		System.out.println("RMI Server ready");
	}
}
