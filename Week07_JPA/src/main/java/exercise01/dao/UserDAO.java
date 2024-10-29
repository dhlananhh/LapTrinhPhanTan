package exercise01.dao;

import java.util.List;

import exercise01.entities.User;
import jakarta.persistence.EntityManager;

public class UserDAO {
	private EntityManager entityManager;
	
	
	public UserDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public User insertNewUser (User user) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
            e.printStackTrace();
		}
		
		return null;
	}
	
	
	public User findById (int id) {
		User user = entityManager.find(User.class, id);
		return user;
	}
	
	
	public List<User> findAll() {
		return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
	}
	
	
	public void findByUsername(String username) {
		entityManager.createNamedQuery("User.findByUsername", User.class).setParameter("username", username)
				.getResultList();
	}
}
