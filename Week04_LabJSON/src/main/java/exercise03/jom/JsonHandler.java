package exercise03.jom;


import exercise03.entities.State;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonWriter;


public class JsonHandler {
	private static List<State> s = fromJson("json/states.json");
	
	
	public static State findByAb (String abb) {
		for (State state : s) {
			if (state.getAbbreviation().equalsIgnoreCase(abb)) {
				return state;
			}
		}
		return null;
	}
	
	
	public static List<State> findByYear (int year) {
		List<State> result = new ArrayList<State>();
		for (State state : s) {
			if (state.getStateHood() == year) {
				result.add(state);
			}
		}
		return result;
	}
	
	
	public static List<State> fromJson (String fileName) {
		List<State> states = new ArrayList<State>();
		try (JsonReader reader = Json.createReader(new FileReader(fileName))) {
			JsonArray jsonArray = reader.readArray();
			for (JsonValue jsonValue : jsonArray) {
				if (jsonValue instanceof JsonObject) {
					JsonObject jo = jsonValue.asJsonObject();
					State s = new State();
					s.setStateName(jo.getString("StateName"));
					s.setAbbreviation(jo.getString("Abbreviation"));
					s.setCapital(jo.getString("Capital"));
					s.setStateHood(jo.getInt("StateHood"));
					s.setStateID(jo.getInt("ID"));
					states.add(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonException e) {
	        System.err.println("Error parsing JSON file: " + e.getMessage());
	    }
		return states;
	}
	
	
	public static void toJsonFile (List<State> states, String fileName) {
		try (JsonWriter writer = Json.createWriter(new FileWriter(fileName))) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (State state : states) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                		.add("ID", state.getStateID())
                        .add("StateName", state.getStateName())
                        .add("Abbreviation", state.getAbbreviation())
                        .add("Capital", state.getCapital())
                        .add("StateHood", state.getStateHood());
                JsonObject stateObject = objectBuilder.build();
                arrayBuilder.add(stateObject);
//                arrayBuilder.add("\n");
            }
            JsonArray jsonArray = arrayBuilder.build();
            writer.writeArray(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
