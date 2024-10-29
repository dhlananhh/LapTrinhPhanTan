package exercise01.utils;


import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MongoDBConnection {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DATABASE_NAME = "BikeStores";
	private MongoClient mongoClient;
	private MongoDatabase mongoDB;

	
	public MongoDBConnection() {
		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
		this.mongoClient = MongoClients.create(connectionString);
		this.mongoDB = this.mongoClient.getDatabase(DATABASE_NAME);
	}
	
	
	public MongoClient getClient() {
		return this.mongoClient;
	}
	
	
	public MongoDatabase getDatabase() {
		return this.mongoDB;
	}
	

	public void close() {
		this.mongoClient.close();
	}

	
	public static void closeConnection(MongoDBConnection mongoDBConnection) {
        mongoDBConnection.close();
	}
	
}
