package services;


import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.List;

import dao.ProjectDAO;
import entities.Project;


public class ProjectService extends UnicastRemoteObject implements ProjectDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	public ProjectService (EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}

	@Override
	public boolean addProject(Project project) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProject(Project project) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProject(long projectID) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Project getProject(String projectName) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getProjects(String employeeEmail) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
