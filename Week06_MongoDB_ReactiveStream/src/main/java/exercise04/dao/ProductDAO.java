package exercise04.dao;


import exercise04.entities.Product;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ProductDAO {
	private MongoCollection<Product> productCollection;
	
	
	public ProductDAO (MongoDatabase db) {
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromProviders(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		productCollection = db.getCollection("products", Product.class).withCodecRegistry(pojoCodecRegistry);
	}
	
	
	
	public boolean insertOneNewProduct (Product product) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		
		Publisher<InsertOneResult> pub = productCollection.insertOne(product);
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
	
	
	/*
	   db.products.aggregate([
	   	{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }, 
	   	{ $unwind: '$ps' }, 
	   	{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }, 
	   	{ $replaceWith: '$ps' }
	   ])
	   
	 */
	public List<Product> getProductMaxPrice() throws InterruptedException {

		CountDownLatch latch = new CountDownLatch(1);
		List<Product> products = new ArrayList<Product>();
		AggregatePublisher<Product> pub = productCollection
				.aggregate(Arrays.asList(
						Aggregates.group(null, Accumulators.max("maxPrice", "$price"),
								Accumulators.addToSet("ps", "$$ROOT")),
						Aggregates.unwind("$ps"),
						Aggregates.match(Filters.expr(Filters.eq("$eq", Arrays.asList("$ps.price", "$maxPrice")))),
						Aggregates.replaceWith("$ps")

				));

		Subscriber<Product> sub = new Subscriber<Product>() {

			private Subscription s;
			
			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Product t) {
				products.add(t);
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
		
		latch.await();
		
		return products;
	}
	
	
	
	public List<Document> getProductMaxPriceUsingDocumentParse() {
	    List<Document> products = new ArrayList<>();

	    Document group = Document.parse("{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }");
	    Document unwind = Document.parse("{ $unwind: '$ps' }");
	    Document match = Document.parse("{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }");
	    Document replaceWith = Document.parse("{ $replaceWith: '$ps' }");

	    AggregatePublisher<Product> result = productCollection.aggregate(Arrays.asList(group, unwind, match, replaceWith));

	    ((List<Document>) result).iterator().forEachRemaining(products::add);

	    return products;
	}
}
