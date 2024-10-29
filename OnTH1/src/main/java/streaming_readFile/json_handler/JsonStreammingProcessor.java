package streaming_readFile.json_handler;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;
import streaming_readFile.entities.State;

public class JsonStreammingProcessor {
	public static void main(String[] args) throws FileNotFoundException {
		
		FileInputStream fis = new FileInputStream("json/states.json");
		JsonParser jsonParser = Json.createParser(fis);
		
		List<State> states = new ArrayList<>();
		
		while(jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == JsonParser.Event.START_OBJECT) {
				State state = new State();
				while(jsonParser.hasNext()) {
					event = jsonParser.next();
					if(event == JsonParser.Event.KEY_NAME) {
						String keyName = jsonParser.getString();
						jsonParser.next();
						
						switch(keyName) {
							case "StateName":
								state.setStateName(jsonParser.getString());
								break;
							case "Abbreviation":
								state.setAbbreviation(jsonParser.getString());
								break;
							case "Capital":
								state.setCapital(jsonParser.getString());
								break;
							case "Statehood":
								state.setStateHood(jsonParser.getInt());
								break;
							case "ID":
								state.setId(jsonParser.getInt());
								break;
						}
					}
					else if(event == JsonParser.Event.END_OBJECT) {
						states.add(state);
						break;
					}
				} 
			} // end of object loop
		} // end of array loop
		for (State state : states) {
			System.out.println(state);
		}
	}
}