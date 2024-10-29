package exercise04.demo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise04.dao.OrderDAO;
import exercise04.entities.Order;
import exercise04.utils.DBConnection;

public class OrderDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		OrderDAO orderDAO = new OrderDAO(db);
		
//		List<Order> lstOrders = orderDAO.get();
//		for (Order order : lstOrders) {
//			System.out.println(order);
//		}
	}
}
