package test.restaurants;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mongodb.reactivestreams.client.MongoDatabase;

import restaurants.dao.RestaurantDAO;
import restaurants.entities.Restaurant;
import restaurants.utils.DBConnection_Restaurants;


public class RestaurantTestDemo {
	private static RestaurantDAO restaurantDAO;
	
	
	@BeforeAll
	static void setUp() {
		DBConnection_Restaurants conn = new DBConnection_Restaurants();
		MongoDatabase db = conn.getInstance().getDatabase();
		restaurantDAO = new RestaurantDAO(db);
	}


	// hàm test lấy DS nhà hàng theo borough và cuisine
	@Test
	@DisplayName("testGetOneRestaurant")
	void testGetOneRestaurant() {
		String restaurantId = "40356018";
		
		Restaurant res = restaurantDAO.getOneRestaurant(restaurantId);
		
		assertEquals("40356018", res.getRestaurantId());
		System.out.println("Success !!!");
	}
	
	
	// hàm test updateTheBuildingOfAddress
	@Test
	@DisplayName("testUpdateTheBuildingOfAddress")
	void testUpdateTheBuildingOfAddress() {
		String restaurantId = "40356018";
		String name = "Riviera Caterer";
		String buildingUpdated = "3780";

		restaurantDAO.updateTheBuildingOfAddress(restaurantId, name, buildingUpdated);

		Restaurant res = restaurantDAO.getOneRestaurant(restaurantId);

		assertEquals("3780", res.getAddress().getBuilding());
		System.out.println("Success !!!");
	}
	
	
	// hàm test insertOneNewRestaurant
	@Test
	@DisplayName("testInsertOneNewRestaurant")
	void testInsertOneNewRestaurant() {
		Restaurant res = new Restaurant();
		res.setRestaurantId("99999999");
		res.setName("Test Restaurant");
		res.setCuisine("Test Cuisine");
		res.setBorough("Test Borough");
		res.setAddress(null);

		restaurantDAO.insertOneNewRestaurant(res);

		Restaurant resNew = restaurantDAO.getOneRestaurant("99999999");

		assertEquals("99999999", resNew.getRestaurantId());
		System.out.println("Success !!!");
	}
	
}
