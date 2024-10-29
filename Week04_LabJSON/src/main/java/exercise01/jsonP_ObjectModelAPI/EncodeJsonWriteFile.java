package exercise01.jsonP_ObjectModelAPI;


import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


// Dùng để ghi file JSON và xuất ra màn hình kết quả

/*
	Chuyển đổi từ đối tượng sang JSON
	-> sd Object Model API
	=> đọc object -> write file
*/
public class EncodeJsonWriteFile {
	public static void main (String[] args) throws Exception {
		EncodeJsonWriteFile encodeJsonFile = new EncodeJsonWriteFile();
		
		Employee emp = new Employee(10001, "John Smith", 10000d);
		
		String js = encodeJsonFile.generateToJson(emp);
		System.out.println("Employee: ");
		System.out.println(js);
		encodeJsonFile.export("json/employee_output.json", js);
	}
	
	
	private String generateToJson (Employee p) {
		JsonObject jsonObject = Json.createObjectBuilder()
			.add("id", p.getId())
			.add("name", p.getName())
			.add("salary", p.getSalary())
			.build();
		return jsonObject.toString();
	}
	
	
	private void export (String filePath, String json) throws Exception {
		PrintWriter out = new PrintWriter(new FileOutputStream((filePath), true));
		out.println(json);
		out.close();
	}
}
