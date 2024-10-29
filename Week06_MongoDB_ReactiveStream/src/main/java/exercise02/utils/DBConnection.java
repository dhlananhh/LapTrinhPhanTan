package exercise02.utils;


import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;


public class DBConnection {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DATABASE_NAME = "sample_training";
	
	
	private MongoClient client;
	private MongoDatabase db;
	
	private DBConnection instance;
	
	
	public DBConnection() {
		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
		this.client = MongoClients.create(connectionString);
		this.db = this.client.getDatabase(DATABASE_NAME);
	}
	
	
	public DBConnection getInstance() {
		if (this.instance == null) {
			this.instance = new DBConnection();
		}
		
		return this.instance;
	}
	
	
	public MongoClient getClient() {
		return this.client;
	}
	
	
	public MongoDatabase getDatabase() {
		return this.db;
	}
}
