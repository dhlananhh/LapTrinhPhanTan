package streaming_readFile.json_handler;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import streaming_readFile.entities.Person;


public class JsonParseJavaObject_Person {
	public static void main (String[] args) throws FileNotFoundException {
		// đọc file JSON
		InputStream is = new FileInputStream("json/person.json");
		final JsonParser parser = Json.createParser(is);
		
		// tạo mới đối tượng person
		Person person = new Person();
		
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
						case "firstName":
							person.setFirstName(parser.getString());
							break;
						case "lastName":
							person.setFirstName(parser.getString());
							break;
						case "age":
							person.setAge(parser.getInt());
							break;
						case "address":
							
					}
					break;

				case END_OBJECT:
					break;
			}
		}
	}
}
