package streaming_readFile.json_handler;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonParser;
import streaming_readFile.entities.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class JsonStreamParseJavaObject_Employee {
	public static void main (String[] args) throws FileNotFoundException {
		// đọc file JSON
		InputStream is = new FileInputStream("json/employee.json");
		final JsonParser parser = Json.createParser(is);
		
		// tạo mới đối tượng employee
		Employee employee = new Employee();
		
		// parse ra Java Object
		while (parser.hasNext()) {
			JsonParser.Event event = parser.next();
			
			switch (event) {
				case START_OBJECT:
					break;
				case KEY_NAME:
					String keyName = parser.getString();
					parser.next();
					
					switch (keyName) {
						case "name":
							employee.setName(parser.getString());
							break;
						case "isRetired":
							employee.setRetired((parser.getValue() == JsonValue.TRUE) ? true : false);
							break;
						case "age":
							employee.setAge(parser.getInt());
							break;
						case "skills":
							List<String> skills = new ArrayList<String>();
							while (parser.next() != JsonParser.Event.END_ARRAY) {
								skills.add(parser.getString());
							}
							employee.setSkills(skills);
							break;
					}
					
				case END_OBJECT:
					break;
			}
		}
		
		parser.close();
		
		System.out.println(employee);
	}
}
