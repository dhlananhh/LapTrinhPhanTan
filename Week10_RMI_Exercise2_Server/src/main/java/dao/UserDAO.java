package dao;


import entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


public interface UserDAO extends Remote {
	public boolean addUser (User user) throws RemoteException;
	public Map<User, Integer> getStatistics() throws RemoteException;
	public List<User> listAllUsers() throws RemoteException;
}
