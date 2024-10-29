package app;

import java.util.List;
import jakarta.persistence.EntityManager;
import dao.StaffDAO;
import entity.Staff;
import services.EntityManagerFactoryUtil;
import services.StaffService;

public class Test {
	public static void main(String[] args) {
		EntityManagerFactoryUtil mangerFactoryUtil = new EntityManagerFactoryUtil();
		EntityManager entityManager = mangerFactoryUtil.getEntityManager();

		StaffDAO studentDAO = new StaffService(entityManager);
		
		

		mangerFactoryUtil.closeEntityManager();
		mangerFactoryUtil.closeEntityManagerFactory();
	}
}
