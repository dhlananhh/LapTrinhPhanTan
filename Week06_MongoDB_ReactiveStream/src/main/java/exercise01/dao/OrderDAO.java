package exercise01.dao;


import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import exercise01.entities.Order;


public class OrderDAO {
	private MongoCollection<Order> orderCollection;
	
	
	public OrderDAO (MongoDatabase db) {
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
			
		orderCollection = db.getCollection("order", Order.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	
	// thêm mới 1 đơn hàng
	public boolean insertOneNewOrder (Order newOrder) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> pub = orderCollection.insertOne(newOrder);
		Subscriber<InsertOneResult> sub = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(InsertOneResult t) {
                result.set(t.getInsertedId() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Successfully inserted new order");
			}
		};
		
		return result.get();
	}
}
