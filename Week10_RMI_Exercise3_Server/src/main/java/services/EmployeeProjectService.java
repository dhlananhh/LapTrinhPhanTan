package services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dao.EmployeeProjectDAO;
import jakarta.persistence.EntityManager;

public class EmployeeProjectService extends UnicastRemoteObject implements EmployeeProjectDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	
	public EmployeeProjectService (EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

}
