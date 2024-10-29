package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.Project;

public interface ProjectDAO extends Remote {
	public boolean addProject (Project project) throws RemoteException;
	public boolean updateProject (Project project) throws RemoteException;
	public boolean deleteProject (long projectID) throws RemoteException;
	public Project getProject (String projectName) throws RemoteException;
	public List<Project> getProjects (String employeeEmail) throws RemoteException;
	
}
