package services;


import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class EntityManagerFactoryUtil {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	
	public EntityManagerFactoryUtil() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("employeeproject_server_mariadb");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	
	public void closeEntityManager() {
		this.entityManager.close();
	}
	
	
	public void closeEntityManagerFactory() {
		this.entityManagerFactory.close();
	}
}
