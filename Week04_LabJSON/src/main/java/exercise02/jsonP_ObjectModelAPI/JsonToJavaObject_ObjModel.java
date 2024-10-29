package exercise02.jsonP_ObjectModelAPI;


import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonArray;

//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.json.JsonReader;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;


/*
	Chuyển đổi JSON sang đối tượng 
	bằng cách sử dụng JSON-P Object Model API
	-> Sử dụng JsonObjectBuilder, JsonArrayBuilder và các phương thức tương ứng
	=> Để trích xuất dữ liệu từ JSON
*/

public class JsonToJavaObject_ObjModel {
	public static void main (String[] args) {
		// Chuỗi JSON đầu vào
        String json = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"age\":25,\"address\":{\"streetAddress\":\"21 2nd Street\",\"city\":\"New York\",\"state\":\"NY\",\"postalCode\":10021},\"phoneNumbers\":[{\"type\":\"home\",\"number\":\"212 555-1234\"},{\"type\":\"fax\",\"number\":\"646 555-4567\"}]}";
        
        // Chuyển đổi JSON sang đối tượng
        JsonObject person = Json.createReader(new StringReader(json)).readObject();
        JsonObject address = person.getJsonObject("address");
        JsonArray phoneList = person.getJsonArray("phoneNumbers");
        
        // Trích xuất dữ liệu từ đối tượng
        String firstName = person.getString("firstName");
        String lastName = person.getString("lastName");
        int age = person.getInt("age");
        
        String streetAddress = address.getString("streetAddress");
        String city = address.getString("city");
        String state = address.getString("state");
        int postalCode = address.getInt("postalCode");
        
        // In kết quả ra màn hình
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Street Address: " + streetAddress);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        System.out.println("Postal Code: " + postalCode);
        
        for (JsonValue phoneNumber : phoneList) {
        	JsonObject phone = (JsonObject) phoneNumber;
        	String type = phone.getString("type");
        	String number = phone.getString("number");
        	System.out.println("Phone Number - Type: " + type + ", Number: " + number);
        }
	}
}
