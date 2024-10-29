package exercise04.dao;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import exercise04.entities.Order;


public class OrderDAO {
	private MongoCollection<Order> orderCollection;
	
	
	public OrderDAO (MongoDatabase db) {
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		orderCollection = db.getCollection("orders", Order.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	
	public boolean insertOneNewOrder (Order order) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> pub = orderCollection.insertOne(order);
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
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result.get();
	}
	
	
	public boolean insertManyNewOrders (List<Order> orders) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
	
		Publisher<InsertManyResult> pub = orderCollection.insertMany(orders);
		Subscriber<InsertManyResult> sub = new Subscriber<InsertManyResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(InsertManyResult t) {
				result.set(t.getInsertedIds() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return result.get();
	}
	
	
	// Lọc DS Order có giá cao nhất
	/*
	MONGO QUERY:
		db.products.aggregate ([
		   	{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }, 
		   	{ $unwind: '$ps' }, 
		   	{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }, 
		   	{ $replaceWith: '$ps' }
	   	])
	*/
	public List<Order> getOrderMaxPrice() {
		List<Order> orders = new ArrayList<Order>();
		CountDownLatch latch = new CountDownLatch(1);
		
		AggregatePublisher<Order> pub = orderCollection.aggregate(Arrays.asList(
			Aggregates.group(null, 
				Accumulators.max("maxPrice", "$order_total"),
				Accumulators.addToSet("ps", "$$ROOT")
				),
			Aggregates.unwind("$ps"),
			Aggregates.match(Filters.expr(Filters.eq("$eq", Arrays.asList("$ps.price", "$maxPrice")))),
			Aggregates.replaceWith("$ps")
		));
		
		Subscriber<Order> sub = new Subscriber<Order>() {
			Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Order t) {
				orders.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		};
		
		pub.subscribe(sub);
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return orders;
	}
	

	
}
