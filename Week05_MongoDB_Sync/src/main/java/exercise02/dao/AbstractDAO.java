package exercise02.dao;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class AbstractDAO {
	private static AbstractDAO instance = null;
	private static MongoClient client;
	protected MongoDatabase db;
	
	
	protected AbstractDAO (MongoClient mongoclient) {
		String DB_NAME = "sample_training";
	    String URL_MONGO = "mongodb://localhost:27017/";
	    ConnectionString connectionString = new ConnectionString(
	        URL_MONGO);
	    MongoClientSettings settings = MongoClientSettings.builder()
	        .applyConnectionString(connectionString)
	        .serverApi(ServerApi.builder()
	            .version(ServerApiVersion.V1)
	            .build())
	        .build();
	    client = MongoClients.create(settings);
	    this.db = client.getDatabase(DB_NAME);
	}
	
	
	public static synchronized AbstractDAO getInstance() {
		if (instance == null)
			instance = new AbstractDAO(client);
		return instance;
	}
	
	
	public MongoClient getClient() {
		return client;
	}
	
	
	public MongoDatabase getDb() {
		return db;
	}
}
