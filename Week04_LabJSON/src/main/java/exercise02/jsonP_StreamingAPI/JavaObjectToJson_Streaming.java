package exercise02.jsonP_StreamingAPI;


import java.io.StringWriter;
import java.util.List;

import exercise02.entities.*;

import java.util.ArrayList;

import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.json.stream.JsonGenerator;


/*
	Chuyển đổi từ đối tượng sang JSON
	-> sd JSON-P Streaming API
	=> đọc object -> xuất JSON
*/
public class JavaObjectToJson_Streaming {
	public static void main (String[] args) {
		StringWriter writer = new StringWriter();
		JsonGenerator generator = Json.createGenerator(writer);
		
		List<PhoneNumber> phoneList = new ArrayList<PhoneNumber>();
		PhoneNumber home = new PhoneNumber("home", "212 555-1234");
		PhoneNumber fax = new PhoneNumber("fax", "646 555-4567");
		
		phoneList.add(home);
		phoneList.add(fax);
		
		Address address = new Address("21 2nd Street", "New York", "NY", 10021);
		Person person = new Person("John", "Smith", 25, address, phoneList);
		
		generator.writeStartObject();
		generator.write("firstName", person.getFirstName());
		generator.write("lastName", person.getLastName());
		generator.write("address", (JsonValue) person.getAddress());
		
		generator.writeStartArray("phoneNumbers");
		
	}
}
