package restaurants.utils;


import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;


public class DBConnection_Restaurants {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DATABASE_NAME = "DuongHoangLanAnh21087481";
	
	
	private MongoClient client;
	private MongoDatabase db;
	
	private DBConnection_Restaurants instance;
	
	
	public DBConnection_Restaurants() {
		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
		this.client = MongoClients.create(connectionString);
		this.db = this.client.getDatabase(DATABASE_NAME);
	}
	
	
	public DBConnection_Restaurants getInstance() {
		if (this.instance == null) {
			this.instance = new DBConnection_Restaurants();
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
