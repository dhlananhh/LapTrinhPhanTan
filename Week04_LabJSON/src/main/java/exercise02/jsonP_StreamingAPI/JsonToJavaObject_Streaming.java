package exercise02.jsonP_StreamingAPI;


import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;


// Convert từ JSON sang Java Objects bằng JSON-P Streaming API:
/*
	Chuyển đổi từ đối tượng sang JSON
	-> sd JSON-P Streaming API
	=> đọc JSON -> xuất obj
*/

public class JsonToJavaObject_Streaming {
	public static void main (String[] args) {
		final String json = "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"age\":25,\"address\":{\"streetAddress\":\"21 2nd Street\",\"city\":\"New York\",\"state\":\"NY\",\"postalCode\":10021},\"phoneNumbers\":[{\"type\":\"home\",\"number\":\"212 555-1234\"},{\"type\":\"fax\",\"number\":\"646 555-4567\"}]}";
		final JsonParser parser = Json.createParser(new StringReader(json));
		
		while (parser.hasNext()) {
			Event event = parser.next();
			
			switch (event) {
			case KEY_NAME:
				System.out.println(parser.getString());
				break;
			
			case VALUE_STRING:
				System.out.println(parser.getString());
				break;
				
			case END_ARRAY:
				break;
				
			case END_OBJECT:
				break;
				
			case START_ARRAY:
				break;
			
			case START_OBJECT:
				break;
				
			case VALUE_TRUE:
				System.out.println(true);
				break;
				
			case VALUE_FALSE:
				System.out.println(false);
				break;
			
			case VALUE_NULL:
				break;
			
			case VALUE_NUMBER:
				System.out.println(parser.getInt());
				break;
				
			default:
				break;
			}
		}
		
		parser.close();
	}
}
