package exercise01.demo;

import java.util.List;
import java.util.ArrayList;
import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise01.dao.CustomerDAO;
import exercise01.entities.Customer;
import exercise01.utils.DBConnection;

public class CustomerDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		CustomerDAO customerDAO = new CustomerDAO(db);
		
		// Lấy danh sách tất cả các khách hàng
		List<Customer> lstCustomer = customerDAO.getAllCustomers();
		for (Customer customer : lstCustomer) {
			System.out.println(customer);
		}
		
//		Customer cus = customerDAO.getCustomer("11102");
//		System.out.println(cus);
		
		
	}
}
