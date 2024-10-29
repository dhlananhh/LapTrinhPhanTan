package restaurants.demo;


import java.util.ArrayList;
import java.util.List;

import com.mongodb.reactivestreams.client.MongoDatabase;

import restaurants.dao.RestaurantDAO;
import restaurants.entities.Restaurant;
import restaurants.utils.DBConnection_Restaurants;


public class RestaurantDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection_Restaurants conn = new DBConnection_Restaurants();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		RestaurantDAO restaurantDAO = new RestaurantDAO(db);

/*
 * String borough = "Brooklyn"; String cuisine = "Bakery";
 */
	
//		List<Restaurant> getListRestaurants = restaurantDAO.getRestaurantsByBoroughAndCuisine("Queens", "Jewish/Kosher");
//		getListRestaurants.forEach(res -> System.out.println(res));
	
//		for (Restaurant res : getListRestaurants) {
//			System.out.println(res);
//		}
		
//		getListRestaurants.forEach(res -> System.out.println(res));
		
		
//		Restaurant res = restaurantDAO.getOneRestaurant("30075445");
//		System.out.println(res);
		
		
		List<Restaurant> listRestaurants = restaurantDAO.getAvgScoreOfRestaurantByGrade();
		listRestaurants.forEach(res -> System.out.println(res));
	}
}
