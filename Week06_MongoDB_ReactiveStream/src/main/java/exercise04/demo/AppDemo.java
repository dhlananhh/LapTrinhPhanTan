package exercise04.demo;

import java.util.Arrays;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise04.dao.Sample_ProductDAO;
import exercise04.entities.Product;
import exercise04.utils.DBConnection;

public class AppDemo {

	public static void main(String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		Sample_ProductDAO productDAO = new Sample_ProductDAO(db);
//		Product product = new Product(324l, "Brand name 322", "Category name 322", "Product Name 322" , Arrays.asList("yellow"),2022, 11999.99d);
//		productDAO.addProduct(product);
		
		List<Product> products = productDAO.getProductMaxPrice();
		
		products.forEach(p -> System.out.println(p));
	}

}
