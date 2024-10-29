package exercise05.jom;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exercise05.entities.Employee;


public class JacksonDemo {
	private static String json;
	
	public static void main(String[] args) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Employee employee=new Employee(2L, "Kandace Kayden Kingsley", 5000d); 
		
		try {
			json = objectMapper.writeValueAsString(employee);
			System.out.println(json);  
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}  
	}
}
