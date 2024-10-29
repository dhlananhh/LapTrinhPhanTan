package services;


import dao.UserDAO;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class UserService extends UnicastRemoteObject implements UserDAO {
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	
	
	public UserService (EntityManager entityManager) throws RemoteException {
		this.entityManager = entityManager;
	}


	@Override
	public boolean addUser (User user) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			transaction.begin();
			entityManager.persist(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
		return false;
	}


	/*
	Thống kê tổng số lượng các tin tức có các lời bình luận từ từng người dùng
	(chỉ thống kê những người dùng có ít nhất 3 lượt bình luận trở lên).
	Thông tin kết quả gồm thông tin của người dùng và số lượng bình luận cho tin tức.
	*/
	@Override
	public Map<User, Integer> getStatistics() throws RemoteException {
		Map<User, Integer> map = new LinkedHashMap<User, Integer>();
		
		String query = "SELECT u, COUNT(c) FROM User u JOIN u.comments c GROUP BY u HAVING COUNT(c) >= 3";
		
		List<?> list = entityManager.createQuery(query).getResultList();
		
		list.stream().map(record -> (Object[]) record).forEach(record -> {
            User user = (User) record[0];
            Integer count = ((Long) record[1]).intValue();
            map.put(user, count);
        });
		
		return map;
	}


	@Override
	public List<User> listAllUsers() throws RemoteException {
		return entityManager.createNamedQuery("User.listAllUsers", User.class).getResultList();
	}
}
