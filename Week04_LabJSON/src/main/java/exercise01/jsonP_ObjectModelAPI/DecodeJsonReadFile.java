package exercise01.jsonP_ObjectModelAPI;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;


// Dùng để đọc file JSON
/*
	Chuyển đổi từ đối tượng sang JSON
	-> sd Object Model API
	=> đọc json -> xuất object
*/
public class DecodeJsonReadFile {
	public static void main (String[] args) throws FileNotFoundException {
		InputStream in = new FileInputStream("json/employee.json");
		
		// create JsonReader object
		JsonReader reader = Json.createReader(in);
		
		// get JsonObject from JsonReader
		JsonObject jo = reader.readObject();
        
		JsonNumber id = jo.getJsonNumber("id");
		String name = jo.getString("name");
		JsonNumber sal = jo.getJsonNumber("salary");
		
		Employee employee = new Employee(id.longValue(), name, sal.doubleValue());
		
		System.out.println(employee);
	}
}
