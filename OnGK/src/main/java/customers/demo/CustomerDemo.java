package customers.demo;

import java.util.List;

import com.mongodb.reactivestreams.client.MongoDatabase;

import customers.dao.CustomerDAO;
import customers.entities.Customer;
import customers.utils.DBConnection_Customers;

public class CustomerDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection_Customers conn = new DBConnection_Customers();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		CustomerDAO customerDAO = new CustomerDAO(db);
		
		String kwUserName = "pgray87";
		String kwPackages = "Premium";
		
		List<Customer> getListCustomers = customerDAO.getCustomersByUserNameAndPackage(kwUserName, kwPackages);
		getListCustomers.forEach(cus -> System.out.println(cus));
		
	}
}
