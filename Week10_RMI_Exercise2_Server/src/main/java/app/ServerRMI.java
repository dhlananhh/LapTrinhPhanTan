package app;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import jakarta.persistence.EntityManager;
import services.EntityManagerFactoryUtil;

import services.NewsService;
import services.UserService;

import dao.NewsDAO;
import dao.UserDAO;


public class ServerRMI {
	public static void main (String[] args) throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(3002);
		
		EntityManagerFactoryUtil entityManagerFactory = new EntityManagerFactoryUtil();
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		
		UserDAO userDAO = new UserService(entityManager);
		NewsDAO newsDAO = new NewsService(entityManager);
		
		registry.bind("userDAO", userDAO);
		registry.bind("newsDAO", newsDAO);
		
		System.out.println("RMI Server ready");
	}
}
