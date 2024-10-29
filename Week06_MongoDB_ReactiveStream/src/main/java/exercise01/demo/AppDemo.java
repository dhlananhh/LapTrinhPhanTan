package exercise01.demo;

import java.util.Arrays;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise01.dao.ProductDAO;
import exercise01.entities.Product;
import exercise01.utils.DBConnection;

public class AppDemo {

	public static void main(String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		ProductDAO productDAO = new ProductDAO(db);
//		Product product = new Product(324l, "Brand name 322", "Category name 322", "Product Name 322" , Arrays.asList("yellow"),2022, 11999.99d);
//		productDAO.addProduct(product);
		
		List<Product> products = productDAO.getProductMaxPrice();
		products.forEach(p -> System.out.println(p));
		
		Product result = productDAO.find(324l);
		System.out.println(result);
	}

}
