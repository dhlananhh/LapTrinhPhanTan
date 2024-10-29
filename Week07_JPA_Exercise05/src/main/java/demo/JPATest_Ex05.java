package demo;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPATest_Ex05 {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_ORM_MSSQL_Ex05");
	}
}
