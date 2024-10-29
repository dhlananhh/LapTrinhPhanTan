package exercise03.utils;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBConnection {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DB_NAME = "BikeStores"; // "ordersDB";

	private MongoClient client;
	private MongoDatabase db;

	public DBConnection() {
		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
		this.client = MongoClients.create(connectionString);
		this.db = this.client.getDatabase(DB_NAME);
	}

	public MongoClient getClient() {
		return this.client;
	}

	public MongoDatabase getDatabase() {
		return this.db;
	}
}
