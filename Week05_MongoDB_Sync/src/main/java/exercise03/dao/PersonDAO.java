package exercise03.dao;


import org.bson.Document;

import com.mongodb.reactivestreams.client.MongoCollection;


public class PersonDAO {
	private MongoCollection<Document> collection;
	
	
	public PersonDAO (MongoCollection<Document> collection) {
		this.collection = collection;
	}
}
