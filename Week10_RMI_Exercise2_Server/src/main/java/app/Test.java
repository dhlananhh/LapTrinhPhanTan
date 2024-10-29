package app;

import jakarta.persistence.Persistence;

public class Test {
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("staffdb_server_mariadb");
		System.out.println("Database created successfully");
	}
}
