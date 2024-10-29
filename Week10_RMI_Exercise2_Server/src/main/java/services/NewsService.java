package services;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import dao.NewsDAO;
import entities.News;
import jakarta.persistence.EntityManager;


public class NewsService extends UnicastRemoteObject implements NewsDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;

	
	public NewsService (EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}
	

	@Override
	public List<News> getNewsByTagsOrNewsCategories (String value) throws RemoteException {
		return entityManager.createNamedQuery("getNewsByTagsOrNewsCategories", News.class)
				.setParameter("value", "%" + value + "%").getResultList();
	}


	@Override
	public List<News> listAllNews() throws RemoteException {
		return entityManager.createNamedQuery("listAllNews", News.class).getResultList();
	}

}
