package exercise02.jsonP_ObjectModelAPI;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


/*
	Chuyển đổi đối tượng sang JSON 
	bằng cách sử dụng JSON-P Object Model API
	-> Sử dụng JsonObjectBuilder, JsonArrayBuilder và các phương thức tương ứng 
	=> Để xây dựng cấu trúc JSON
*/

public class JavaObjectToJson_ObjModel {
	public static void main (String[] args) {
		// Tạo đối tượng chính
		JsonObjectBuilder personBuilder = Json.createObjectBuilder();
		
		personBuilder.add("firstName", "John");
		personBuilder.add("lastName", "Smith");
		personBuilder.add("age", 25);
		
		// Tạo đối tượng địa chỉ
		JsonObjectBuilder addressBuilder = Json.createObjectBuilder();
		
		addressBuilder.add("streetAddress", "21 2nd Street");
		addressBuilder.add("city", "New York");
		addressBuilder.add("state", "NY");
		addressBuilder.add("postalCode", 10021);
		
		// Thêm đối tượng địa chỉ vào đối tượng chính
		personBuilder.add("address", addressBuilder);
		
		// Tạo mảng số điện thoại
		JsonArrayBuilder phoneListBuilder = Json.createArrayBuilder();
		
		// Thêm các đối tượng số điện thoại vào mảng
		JsonObjectBuilder phoneNumberBuilder1 = Json.createObjectBuilder();
		phoneNumberBuilder1.add("type", "home");
		phoneNumberBuilder1.add("number", "212 555-1234");
		phoneListBuilder.add(phoneNumberBuilder1);
		
		JsonObjectBuilder phoneNumberBuilder2 = Json.createObjectBuilder();
		phoneNumberBuilder2.add("type", "fax");
		phoneNumberBuilder2.add("number", "646 555-4567");
		phoneListBuilder.add(phoneNumberBuilder2);
		
		// Thêm mảng số điện thoại vào đối tượng chính
		personBuilder.add("phoneNumber", phoneListBuilder);
		
		// Chuyển đổi đối tượng sang JSON
		JsonObject person = personBuilder.build();
		String json = person.toString();
		
		// In ra đối tượng JSON
        System.out.println(json);
	}
}
