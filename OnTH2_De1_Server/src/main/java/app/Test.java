package app;

import jakarta.persistence.Persistence;

public class Test {
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("JPA_ORM_MARIADB_OnTH2_De1_Server");
	}
}
