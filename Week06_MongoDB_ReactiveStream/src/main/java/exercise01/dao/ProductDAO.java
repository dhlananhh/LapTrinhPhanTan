package exercise01.dao;


import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;

import exercise01.entities.Product;


public class ProductDAO {
	private MongoCollection<Product> productCollection;

	
	public ProductDAO(MongoDatabase db) {
	
		// Configure the PojoCodecProvider
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();

		// Add the PojoCodecProvider instance to a CodecRegistry. The CodecRegistry
		// allows you to specify one or more codec providers to encode the POJO data
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

		productCollection = db.getCollection("products", Product.class).withCodecRegistry(pojoCodecRegistry);
	}

	
	public Product find(Long id) {
		AtomicReference<Product> result = new AtomicReference<>();
		CountDownLatch latch = new CountDownLatch(1);
		Publisher<Product> pub = productCollection.find(Filters.eq("_id", id)).first();
		Subscriber<Product> sub = new Subscriber<Product>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(Product t) {
				result.set(t);
			}

			@Override
			public void onError(Throwable t) {
				
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
	
	
	public boolean addProduct (Product product) {
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
				System.out.println("Product inserted: " + t.getInsertedId());
				
				result.set(t.getInsertedId() != null ? true : false);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				System.out.println("Completed");
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

	
	
	public Product getProduct(long id) {
		Document doc = new Document("_id", id);
		return (Product) productCollection.find(doc).first();
		
//		return (Product) productCollection.find(Filters.eq("_id", id)).first();
	}
	/*
	public boolean addProduct(Product product) {
		InsertOneResult result = (InsertOneResult) productCollection.insertOne(product);
		
		return result.getInsertedId() != null ? true : false;
	}

	public boolean addProducts(List<Product> products) {
		InsertManyResult result = productCollection.insertMany(products);
		
		return result.getInsertedIds() != null ? true : false;
	}
	
//	 db.products.aggregate([{$group:{_id:'$model_year', num:{$sum:1}}}])
	
	public List<Product> getProductList() {
		List<Product> products = new ArrayList<Product>();
		
		db.products.find({product_name:{$regex:'^Prod*',$options:'i'}})
		Document doc = new Document("product_name", new Document("$regex", "^Prod*").append("$options", "i"));
		
		productCollection
		.find(doc)
		.find(Filters.regex("product_name", "^Prod*", "i"))
		.iterator()
		.forEachRemaining(products::add);
		
		return products;
	}
	
	public Product getProduct(long id) {
		Document doc = new Document("_id", id);
		return productCollection.find(doc).first();
		
		return productCollection.find(Filters.eq("_id", id)).first();
	}
	
	public boolean updateProduct(long id, double newPrice) {
		UpdateResult result = productCollection
				.updateOne(Filters.eq("_id", id),Updates.set("price", newPrice));
		
		return result.getModifiedCount() > 0 ? true : false;
	}
	*/
	
	
	/**
	 *  db.products.aggregate([
	 *  	{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }, 
	 *  	{ $unwind: '$ps' }, 
	 *  	{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }, 
	 *  	{$replaceWith: '$ps'}
	 *  ] )
	 *  
	 */
	/*
	public List<Product> getProductMaxPrice() {
						
		List<Product> products = new ArrayList<Product>();
		AggregateIterable<Product> result = productCollection
				.aggregate(Arrays.asList(
						Aggregates.group(null, Accumulators.max("maxPrice", "$price"), Accumulators.addToSet("ps",  "$$ROOT")),
						Aggregates.unwind("$ps"),	
						Aggregates.match(Filters.expr(Filters.eq("$eq",Arrays.asList("$ps.price", "$maxPrice")))),
						Aggregates.replaceWith("$ps")
						
						));
		
		System.out.println(Arrays.asList(
				Aggregates.group(null, Accumulators.max("maxPrice", "$price"), Accumulators.addToSet("ps",  "$$ROOT")),
				Aggregates.unwind("$ps"),	
				Aggregates.match(Filters.expr(Filters.eq("$eq",Arrays.asList("$ps.price", "$maxPrice")))),
				Aggregates.replaceWith("$ps")	
				));
		
		result.iterator()
		.forEachRemaining(p -> System.out.println(p));
		
		return products;
	}
	*/
	
	/**
	 *  db.products.aggregate([
	 *  	{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }, 
	 *  	{ $unwind: '$ps' }, 
	 *  	{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }, 
	 *  	{ $replaceWith: '$ps' }
	 *  ] )
	 *  
	 */
	/*
	public List<Product> getProductMaxPriceUsingDocumentParse() {
		List<Product> products = new ArrayList<Product>();
		
		Document group = Document.parse("{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }");
		Document unwind = Document.parse("{ $unwind: '$ps' }");
		Document match = Document.parse("{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }");
		Document replaceWith = Document.parse("{ $replaceWith: '$ps' }");
		
		AggregateIterable<Product> result = productCollection
				.aggregate(Arrays.asList(group, unwind, match, replaceWith));
		
		result.iterator()
		.forEachRemaining(p -> System.out.println(p));
		
		return products;
	}
	*/
}
