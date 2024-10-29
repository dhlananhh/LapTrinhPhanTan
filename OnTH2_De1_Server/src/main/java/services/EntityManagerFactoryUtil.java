package services;


import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class EntityManagerFactoryUtil {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	
	public EntityManagerFactoryUtil() {
		entityManagerFactory = Persistence.createEntityManagerFactory("JPA_ORM_MARIADB_OnTH2_De1_Server");
        entityManager = entityManagerFactory.createEntityManager();
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	public void closeEntityManager() {
		entityManager.close();
	}
	
	
	public void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}
}
