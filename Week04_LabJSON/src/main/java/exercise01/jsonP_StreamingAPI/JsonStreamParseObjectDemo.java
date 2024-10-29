package exercise01.jsonP_StreamingAPI;

import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class JsonStreamParseObjectDemo {
	public static void main(String[] args) {
		final String result = "{\"name\":\"John\",\"isRetired\":false,\"age\":42,\"skills\":[\"Java SE\", \"Java EE\"]}";
		final JsonParser parser = Json.createParser(new StringReader(result));
		
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
