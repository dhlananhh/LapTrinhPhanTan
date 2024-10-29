package customers.utils;


import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;


public class DBConnection_Customers {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DATABASE_NAME = "DuongHoangLanAnh21087481";
	
	private MongoClient client;
	private MongoDatabase db;
	
	private DBConnection_Customers instance;
	
	
	public DBConnection_Customers() {
		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
		this.client = MongoClients.create(connectionString);
		this.db = this.client.getDatabase(DATABASE_NAME);
	}
	
	
	public DBConnection_Customers getInstance() {
		if (this.instance == null) {
			this.instance = new DBConnection_Customers();
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
