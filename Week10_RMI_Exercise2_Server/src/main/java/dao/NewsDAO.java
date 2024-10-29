package dao;


import entities.News;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface NewsDAO extends Remote {
	public List<News> getNewsByTagsOrNewsCategories (String value) throws RemoteException;
	public List<News> listAllNews() throws RemoteException;
}
