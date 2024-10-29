package exercise04.demo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise04.dao.ProductDAO;
import exercise04.entities.Product;
import exercise04.utils.DBConnection;

public class ProductDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		ProductDAO productDAO = new ProductDAO(db);
		
		List<Product> lstProducts = productDAO.getProductMaxPrice();
		lstProducts.forEach(p -> System.out.println(p));
		
		
	}
}
