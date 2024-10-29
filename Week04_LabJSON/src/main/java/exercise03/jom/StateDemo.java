package exercise03.jom;


import java.util.List;
import exercise03.entities.State;


public class StateDemo {
	public static void main(String[] args) {
		List<State> listStates = JsonHandler.fromJson("json/states.json");
		System.out.println(listStates);
		
		System.out.println("\nTim theo abb: \n" + JsonHandler.findByAb("AL"));
		System.out.println("\nTim theo nam: \n" + JsonHandler.findByYear(1836));
		
		// xuất file json
		JsonHandler.toJsonFile(listStates, "json/states_output.json");
	}
}
