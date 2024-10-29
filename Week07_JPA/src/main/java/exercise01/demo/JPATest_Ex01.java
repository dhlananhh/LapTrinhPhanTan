package exercise01.demo;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPATest_Ex01 {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA_ORM_MSSQL_Ex01");
	}
}
