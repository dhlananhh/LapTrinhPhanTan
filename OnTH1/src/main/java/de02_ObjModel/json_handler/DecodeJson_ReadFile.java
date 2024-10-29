package de02_ObjModel.json_handler;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

import de02_ObjModel.entities.*;


public class DecodeJson_ReadFile {
	public static void main (String[] args) throws FileNotFoundException {
		InputStream in = new FileInputStream("json/restaurants.json");
		JsonReader jsonReader = Json.createReader(in);
		
		List<Restaurant> lstRestaurants = new ArrayList<Restaurant>();
		JsonArray jsonArray = jsonReader.readArray();
//		JsonObject jsonRestaurant = jsonReader.readObject();
		
		for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
			// đối tượng Restaurant
			JsonNumber restaurantId = jsonObject.getJsonNumber("restaurantId");
			String name = jsonObject.getString("name");
			String borough = jsonObject.getString("borough");
			String cuisine = jsonObject.getString("cuisine");
			boolean isActive = jsonObject.getBoolean("isActive");
			
			// mảng categories
			JsonArray arrCategories = jsonObject.getJsonArray("categories");
			List<String> lstCategories = new ArrayList<String>();
			for (JsonString foodtype : arrCategories.getValuesAs(JsonString.class)) {
				lstCategories.add(foodtype.getString());
			}
			
			// đối tượng address
			JsonObject jsonAddress = jsonObject.getJsonObject("address");
			String building = jsonAddress.getString("building");
			
			// mảng coord
			JsonArray arrCoord = jsonAddress.getJsonArray("coord");
			List<Double> lstCoord = new ArrayList<Double>();
			for (JsonNumber coordinates : arrCoord.getValuesAs(JsonNumber.class)) {
				lstCoord.add(coordinates.doubleValue());
			}
			
			String street = jsonAddress.getString("building");
			String zipcode = jsonAddress.getString("zipcode");
			
			// mảng object grades
			JsonArray arrGrades = jsonObject.getJsonArray("grades");
			List<Grades> lstGrades = new ArrayList<Grades>();
			for (JsonObject gradesObject : arrGrades.getValuesAs(JsonObject.class)) {
				long date = gradesObject.getJsonNumber("date").longValue();
				String grade = gradesObject.getString("grade");
				int score = gradesObject.getInt("score");
				
				lstGrades.add(new Grades(date, grade, score));
			}
			
			// thêm đối tượng mới vào lstRestaurants 
			Address newAddress = new Address(building, lstCoord, street, zipcode);
			Restaurant newRestaurant = new Restaurant(
				restaurantId.longValue(), name, borough, cuisine, 
				isActive, lstCategories, newAddress, lstGrades
			);
			lstRestaurants.add(newRestaurant);
			
			// In kết quả Java Objects ra màn hình
			System.out.println(lstRestaurants);
		}
		
		
		
	}
}
