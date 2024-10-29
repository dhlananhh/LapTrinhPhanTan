package streaming_readFile.json_handler;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParserFactory;
import javax.json.stream.JsonParser.Event;


public class JsonStreamParseJavaArray_States {
	public static void main (String[] args) throws FileNotFoundException {
		InputStream is = new FileInputStream("json/states.json");
//		JsonParser jsonParser = 
	}
}
